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

import com.entity.Coupons;
import com.entity.User;

import com.dto.CouponsBatchDTO;
import com.entity.*;

import com.enums.Role;
import com.exception.ErrorCode;
import com.exception.GeneralException;
import com.exception.GeneralExceptionFactory;
import com.service.*;
import com.utils.cache.TypeInfo;
import com.vo.CouponVO;
import com.vo.CouponsBatchVO;
import com.vo.SingleCouponVO;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

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
    private ICorporationService corporationService;
    @Autowired
    private ICouponsBatchService couponsBatchService;
    @Autowired
    private CorporationBusiness corporationBusiness;
    @Autowired
    private ICouponsService couponsService;
    @Autowired
    private CouponsBatchBussiness couponsBatchBussiness;
    @Autowired
    private TransactionTemplate transactionManager;
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
        couponsBatchDTO.setStock(0);
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
        // check userId and role type
        checkParameters(couponIndividualDTO);
        Coupons coupons = null;
        coupons = transactionManager.execute(status -> {
            CouponsBatch couponsBatch = couponsBatchService.getOne(new LambdaQueryWrapper<CouponsBatch>().eq(CouponsBatch::getBatchId, couponIndividualDTO.getBatchId()).last("for update"));
            if (couponsBatch.getStock() == null || couponsBatch.getStock() <= 0) {
                throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "no more coupons");
            }
            couponsBatch.setStock(couponsBatch.getStock() - 1);
            couponsBatchService.updateById(couponsBatch);
            Coupons co  = setNewIndividualCoupon(couponIndividualDTO);
            couponsService.save(co);
            return co;
        });
        return coupons;
    }

    public void checkParameters(CouponIndividualDTO couponIndividualDTO) {
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

    public Coupons setNewIndividualCoupon(CouponIndividualDTO couponIndividualDTO) {
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
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "no available user");
        }
        CouponVO couponVO = new CouponVO();
        List<SingleCouponVO> res = new ArrayList<>();
        List<Coupons> couponsList = couponsService.list(new LambdaQueryWrapper<Coupons>().eq(Coupons::getUserId, userId).eq(Coupons::getIsUsed, false));
        couponVO.setCouponsList(res);
        if (couponsList == null || couponsList.size() == 0) {
            return couponVO;
        }
        couponsList.forEach(co -> {
            System.out.println(co);
            if (user.getRoleType() == TypeInfo.getIndividualRoleType()) {
                if (!Objects.isNull(co.getValidFrom()) && !Objects.isNull(co.getValidTo()) &&  co.getValidTo().after(new Timestamp(System.currentTimeMillis()))) {
                    CouponsBatch couponsBatch = couponsBatchService.getById(co.getBatchId());
                    SingleCouponVO single = getSingleCouponVO(couponsBatch, co);
                    res.add(single);
                }
            } else if (user.getRoleType() == TypeInfo.getCorporationRoleType()) {
                CouponsBatch couponsBatch = couponsBatchService.getById(co.getBatchId());
                SingleCouponVO single = getSingleCouponVO(couponsBatch, co);
                res.add(single);
            }
        });
        return couponVO;
    }

    private SingleCouponVO getSingleCouponVO(CouponsBatch couponsBatch, Coupons co) {
        SingleCouponVO single = new SingleCouponVO();
        CouponsBatchVO couponsBatchVO = new CouponsBatchVO();
        couponsBatchVO.setBatchId(couponsBatch.getBatchId());
        couponsBatchVO.setDetails(couponsBatch.getDetails());
        couponsBatchVO.setCouponType(couponsBatch.getCouponType());
        couponsBatchVO.setDiscount(couponsBatch.getDiscount());
        couponsBatchVO.setStock(couponsBatch.getStock());
        single.setCouponsBatchVO(couponsBatchVO);
        single.setCoupons(co);
        return single;
    }

    @Override
    @PermissionChecker(requiredRole = Role.ADMIN)
    public void deleteCouponByCouponId(Long couponId) {
        Boolean isSuccess = couponsService.removeById(couponId);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_DELETE_ERROR, "delete coupon failed");
        }
    }

    @Override
    public void invalidateCouponById(Long couponId) {
        Coupons coupons = couponsService.getById(couponId);
        if (coupons == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "no such coupon");
        }
        coupons.setIsUsed(true);
        Boolean isSuccess = couponsService.updateById(coupons);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_UPDATE_ERROR, "invalidate coupon failed");
        }
    }


}
