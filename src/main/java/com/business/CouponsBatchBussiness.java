package com.business;

import com.dto.CouponsBatchDTO;
import com.entity.CouponsBatch;

import java.util.List;

public interface CouponsBatchBussiness {
    CouponsBatch createCouponsBatch(CouponsBatchDTO couponsBatchDTO);

    List<CouponsBatch> getCouponsBatchList();


    List<CouponsBatch> getCouponsBatchListByPage(Long currentPage, Long pageSize);
}
