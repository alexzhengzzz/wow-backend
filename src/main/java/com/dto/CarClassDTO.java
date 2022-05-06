package com.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarClassDTO {
    private String classType;
    private String imageUrl;
    private BigDecimal rentalRatePerDay;
    private BigDecimal dailyMileLimit;
    private BigDecimal overFee;
}
