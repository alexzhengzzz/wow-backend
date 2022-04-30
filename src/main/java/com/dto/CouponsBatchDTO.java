package com.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CouponsBatchDTO {
    private Integer stock;
    private BigDecimal discount;
    // couponType: 1 for individual, 2 for company
    private Character couponType;
    private String details;
}
