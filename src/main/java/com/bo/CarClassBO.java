package com.bo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarClassBO {
    private Integer classId;

    private String classType;

    private String imageUrl;

    private BigDecimal rentalRatePerDay;

    private BigDecimal overFee;
}
