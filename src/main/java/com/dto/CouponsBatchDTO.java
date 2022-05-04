package com.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CouponsBatchDTO {
    private Integer stock;
    @NotNull(message = "please input the discount")
    private BigDecimal discount;
    // couponType: 1 for individual, 2 for company
    @NotNull(message = "please choose the couponType")
    private Character couponType;
    private String details;
}
