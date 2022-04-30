package com.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.business.CorporationBusiness;
import com.business.CouponsBusiness;
import com.dto.CouponCorpDTO;
import com.dto.CouponIndividualDTO;
import com.entity.CouponCust;
import com.entity.Coupons;
import com.entity.User;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.ICorporationService;
import com.service.ICouponCustService;
import com.service.ICouponsService;
import com.service.IUserService;
import com.utils.cache.TypeInfo;
import com.utils.cache.vo.CouponVO;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class CouponsBusinessImpl implements CouponsBusiness {
    @Autowired
    private IUserService userService;

    @Autowired
    private ICouponCustService couponCustService;

    @Autowired
    private ICorporationService corporationService;

    @Autowired
    private CorporationBusiness corporationBusiness;
    @Autowired
    private ICouponsService couponsService;

    @Override
    @Transactional
    public void issueCouponsToCorporation(@NotNull CouponCorpDTO couponCorpDTO) {
        // create coupon_cust template
        Double discount = couponCorpDTO.getDiscount();
        CouponCust couponCust = new CouponCust();
        couponCust.setCouponType(TypeInfo.getCouponCorporationType());
        // list all the employee/user of the corporation
        List<User> lis = corporationBusiness.getEmployeeList(couponCorpDTO.getCompanyName());
        if (lis == null || lis.size() == 0) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
        lis.forEach(user -> {
            // check has corporation coupon
            CouponCust couponCust1 = couponCustService.getOne(new LambdaQueryWrapper<CouponCust>().eq(CouponCust::getCustId, user.getId()));
            // create new coupon
            if (couponCust1 == null) {
                Coupons coupons = new Coupons();
                coupons.setDiscount(discount);
                couponsService.save(coupons);
                Long couponId = coupons.getCouponId();
                couponCust.setCouponId(couponId);
                couponCust.setCustId(user.getId());
                couponCustService.save(couponCust);
            } else {
                Long couponId = couponCust1.getCouponId();
                couponsService.update(new LambdaUpdateWrapper<Coupons>().set(Coupons::getDiscount, discount).eq(Coupons::getCouponId, couponId));
            }
        });
        return;
    }

    @Override
    public void issueCouponsToIndividual(CouponIndividualDTO couponIndividualDTO) {
        // check userId role type
        User user = userService.getById(couponIndividualDTO.getUserId());
        if (user == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
        if (user.getRoleType() != TypeInfo.getIndividualRoleType()) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
        Coupons coupons = new Coupons();
        coupons.setDiscount(couponIndividualDTO.getDiscount());
        coupons.setValidFrom(couponIndividualDTO.getValidFrom());
        coupons.setValidTo(couponIndividualDTO.getValidTo());
        Boolean isSuccess = couponsService.save(coupons);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
        Long couponId = coupons.getCouponId();
        CouponCust couponCust = new CouponCust();
        couponCust.setCouponId(couponId);
        couponCust.setCustId(couponIndividualDTO.getUserId());
        couponCust.setCouponType(TypeInfo.getCouponIndividualType());
        isSuccess = couponCustService.save(couponCust);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
    }

    @Override
    public CouponVO getValidCouponsByUserId(Long userId) {
        // check userId role type
        User user = userService.getById(userId);
        if (user == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
        if (user.getRoleType() != TypeInfo.getIndividualRoleType() && user.getRoleType() != TypeInfo.getCorporationRoleType()) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR);
        }
        CouponVO couponVO = new CouponVO();
        List<Coupons> couponList = new ArrayList<>();
        couponVO.setCouponsList(couponList);
        List<CouponCust> couponCustList = couponCustService.list(new LambdaQueryWrapper<CouponCust>().eq(CouponCust::getCustId, userId));
        if (couponCustList == null || couponCustList.size() == 0) {
            return couponVO;
        }
        couponCustList.forEach(couponCust -> {
            Coupons coupons = couponsService.getById(couponCust.getCouponId());
            if (user.getRoleType() == TypeInfo.getIndividualRoleType()) {
                if (!Objects.isNull(coupons.getValidFrom()) && !Objects.isNull(coupons.getValidTo()) &&  coupons.getValidTo().getTime() > System.currentTimeMillis()) {
                    couponVO.getCouponsList().add(coupons);
                }
            } else if (user.getRoleType() == TypeInfo.getCorporationRoleType()) {
                couponVO.getCouponsList().add(coupons);
            }
        });
        return couponVO;
    }

    @Override
    public void deleteCouponByCouponId(Long couponId) {
        Boolean isSuccess = couponsService.removeById(couponId);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_DELETE_ERROR);
        }
        isSuccess = couponCustService.remove(new LambdaQueryWrapper<CouponCust>().eq(CouponCust::getCouponId, couponId));
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_DELETE_ERROR);
        }
    }

//    private Corporation getCorportaion(CouponCorpDTO couponCorpDTO) {
//        String companyName = couponCorpDTO.getCompanyName();
//        Corporation corporation = corporationService.getCorporationByName(companyName);
//        return corporation;
//    }
}
