package com.vo;

import com.entity.Coupons;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CouponVO {
    private List<Coupons> couponsList;
}
