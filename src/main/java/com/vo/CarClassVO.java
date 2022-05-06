package com.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarClassVO {
    private Integer classId;

    private String classType;

    private String imageUrl;

    private BigDecimal dailyMileLimit;

    private BigDecimal rentalRatePerDay;

    private BigDecimal overFee;
}
