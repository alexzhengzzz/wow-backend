package com.business;

import com.dto.CouponsBatchDTO;
import com.entity.CouponsBatch;

public interface CouponsBatchBussiness {
    CouponsBatch createCouponsBatch(CouponsBatchDTO couponsBatchDTO);
}
