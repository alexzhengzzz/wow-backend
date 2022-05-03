package com.business.impl;

import com.business.CouponsBatchBussiness;
import com.dto.CouponsBatchDTO;
import com.entity.CouponsBatch;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.interceptor.CachePrepareServiceImpl;
import com.service.ICouponsBatchService;
import com.utils.cache.TypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CouponsBatchBussinessImpl implements CouponsBatchBussiness {
    @Autowired
    private ICouponsBatchService couponsBatchService;

    @Override
//    @PermissionChecker(requiredRole = Role.ADMIN) // only admin can create coupons batch
    public CouponsBatch createCouponsBatch(CouponsBatchDTO couponsBatchDTO) {
        CouponsBatch couponsBatch = new CouponsBatch();
        checkAndSetParameters(couponsBatchDTO, couponsBatch);
        boolean isSuccess = couponsBatchService.save(couponsBatch);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "create couponsBatch failed");
        }
        return couponsBatch;
    }
    @Autowired
    private CachePrepareServiceImpl cachePrepareService;
    private void checkAndSetParameters(CouponsBatchDTO couponsBatchDTO, CouponsBatch couponsBatch) {
        if (couponsBatchDTO == null || couponsBatchDTO.getDiscount() == null || couponsBatchDTO.getDiscount().compareTo(BigDecimal.ZERO) <= 0) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "couponsBatch is null or discount is null");
        }
        Integer stock = couponsBatchDTO.getStock();
        if (couponsBatchDTO.getCouponType().equals(TypeInfo.getCouponIndividualType())) {
            if (stock == null || stock <= 0) {
                throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "invalid stock");
            }
            // add to bloom filter
            //if (cachePrepareService.getCouponsBatchBloomFilter() != null) {
            //    cachePrepareService.getCouponsBatchBloomFilter().put(couponsBatch.getBatchId());
            //}
        } else if (couponsBatchDTO.getCouponType().equals(TypeInfo.getCouponCorporationType())) {
            stock = null;
        }
        couponsBatch.setCouponType(couponsBatchDTO.getCouponType());
        couponsBatch.setDiscount(couponsBatchDTO.getDiscount());
        couponsBatch.setStock(stock);
        couponsBatch.setDetails(couponsBatchDTO.getDetails());
    }
}
