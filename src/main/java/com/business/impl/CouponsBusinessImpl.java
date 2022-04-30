package com.business.impl;

import cn.hutool.core.date.DateUtil;
import com.annotation.PermissionChecker;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.business.CorporationBusiness;
import com.business.CouponsBatchBussiness;
import com.business.CouponsBusiness;
import com.dto.CouponCorpDTO;
import com.dto.CouponIndividualDTO;

import com.entity.CouponCust;
import com.entity.Coupons;
import com.entity.User;

import com.dto.CouponsBatchDTO;
import com.entity.*;

import com.enums.Role;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.*;
import com.utils.cache.TypeInfo;
import com.utils.cache.vo.CouponVO;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
    private ICouponsBatchService couponsBatchService;
    @Autowired
    private CorporationBusiness corporationBusiness;
    @Autowired
    private ICouponsService couponsService;
    @Autowired
    private CouponsBatchBussiness couponsBatchBussiness;
    @Override
    @Transactional
    public List<Coupons> issueCouponsToCorporation(@NotNull CouponCorpDTO couponCorpDTO) {
        // create a new batch of coupons
        CouponsBatchDTO couponsBatchDTO = setCouponsBatchDTO(couponCorpDTO);
        CouponsBatch couponsBatch = couponsBatchBussiness.createCouponsBatch(couponsBatchDTO);
        Long batchId = couponsBatch.getBatchId();

        // list all the employee/user of the corporation
        List<User> lis = corporationBusiness.getEmployeeList(couponCorpDTO.getCompanyName());
        if (lis == null || lis.size() == 0) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
        List<Coupons> res = new ArrayList<>();
        lis.forEach(user -> {
            // corporation only has one kind of coupon at a time
            Coupons co = couponsService.getOne(new LambdaQueryWrapper<Coupons>().eq(Coupons::getUserId, user.getId()));
            // create new coupon
            if (co == null) {
                Coupons coupons = setNewCoupon(couponCorpDTO, user, batchId);
                couponsService.save(coupons);
                res.add(coupons);
            } else {
                Long couponId = co.getCouponId();
                co.setBatchId(batchId);
                couponsService.update(new LambdaUpdateWrapper<Coupons>().set(Coupons::getBatchId, batchId).eq(Coupons::getCouponId, couponId));
                res.add(co);
            }
        });
        return res;
    }

    private CouponsBatchDTO setCouponsBatchDTO(CouponCorpDTO couponCorpDTO) {
        CouponsBatchDTO couponsBatchDTO = new CouponsBatchDTO();
        couponsBatchDTO.setDiscount(couponCorpDTO.getDiscount());
        couponsBatchDTO.setCouponType(TypeInfo.getCouponCorporationType());
        couponsBatchDTO.setStock(null);
        couponsBatchDTO.setDetails(couponCorpDTO.getDetails());
        return couponsBatchDTO;
    }

    private Coupons setNewCoupon(CouponCorpDTO couponCorpDTO, User user, Long batchId) {
        Coupons coupons = new Coupons();
        coupons.setUserId(user.getId());
        coupons.setValidTo(null);
        coupons.setValidFrom(null);
        coupons.setBatchId(batchId);
        coupons.setIsUsed(false);
        return coupons;
    }

    @Override
    @Transactional
    public Coupons issueCouponsToIndividual(CouponIndividualDTO couponIndividualDTO) {
        // check userId role type
        checkParameters(couponIndividualDTO);
        CouponsBatch couponsBatch = couponsBatchService.getById(couponIndividualDTO.getBatchId());
        if (couponsBatch.getStock() == null || couponsBatch.getStock() <= 0) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "no more coupons");
        }
        Coupons coupons = setNewIndividualCoupon(couponIndividualDTO);
        Boolean isSuccess = couponsService.save(coupons);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
        couponsBatch.setStock(couponsBatch.getStock() - 1);
        isSuccess = couponsBatchService.updateById(couponsBatch);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
        return coupons;
    }

    private void checkParameters(CouponIndividualDTO couponIndividualDTO) {
        User user = userService.getById(couponIndividualDTO.getUserId());
        if (user == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "no such individual user");
        }
        if (user.getRoleType() != TypeInfo.getIndividualRoleType()) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "user id error");
        }
        Timestamp start = couponIndividualDTO.getValidFrom();
        Timestamp end = couponIndividualDTO.getValidTo();
        if (start == null && end == null) return; // forever valid
        if (start == null || end == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "illegal time");
        }
        if (DateUtil.compare(start, end) <= 0 && DateUtil.compare(end, new Timestamp(System.currentTimeMillis())) <= 0) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "illegal time");
        }
    }

    private Coupons setNewIndividualCoupon(CouponIndividualDTO couponIndividualDTO) {
        Coupons coupons = new Coupons();
        coupons.setUserId(couponIndividualDTO.getUserId());
        coupons.setBatchId(couponIndividualDTO.getBatchId());
        coupons.setIsUsed(false);
        coupons.setValidTo(couponIndividualDTO.getValidTo());
        coupons.setValidFrom(couponIndividualDTO.getValidFrom());
        return coupons;
    }

    @Override
    public CouponVO getValidCouponsByUserId(Long userId) {
        // check userId role type
        User user = userService.getById(userId);
        if (user == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "no such userId");
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
    @PermissionChecker(requiredRole = Role.ADMIN)
    public void deleteCouponByCouponId(Long couponId) {
        Boolean isSuccess = couponsService.removeById(couponId);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_DELETE_ERROR, "delete coupon failed");
        }
        isSuccess = couponCustService.remove(new LambdaQueryWrapper<CouponCust>().eq(CouponCust::getCouponId, couponId));
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_DELETE_ERROR);
        }
    }


}
