package com.vo;

import com.entity.Coupons;
import com.entity.CouponsBatch;
import lombok.Data;

@Data
public class SingleCouponVO {
    Coupons coupons;
    CouponsBatchVO couponsBatchVO;
}
