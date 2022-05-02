package com.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CouponsBatchVO {
    private Long batchId;
    private Integer stock;
    private BigDecimal discount;
    private Character couponType;
    private String details;
}
