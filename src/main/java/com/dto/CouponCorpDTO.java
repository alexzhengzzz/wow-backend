package com.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CouponCorpDTO {
    private String companyName;
    private BigDecimal discount;
    private String details;
}
