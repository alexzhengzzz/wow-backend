package com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class CarClassDTO {
    @NotBlank(message = "classType is required")
    private String classType;
    private String imageUrl;
    @NotBlank(message = "rental rate is required")
    private BigDecimal rentalRatePerDay;
    @NotBlank(message = "daily mile limit is required")
    private BigDecimal dailyMileLimit;
    @NotBlank(message = "over fee is required")
    private BigDecimal overFee;
}
