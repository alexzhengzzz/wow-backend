package com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CouponCorpDTO {
    @NotBlank(message = "Please enter a companyName")
    private String companyName;
    @NotNull(message = "Please enter a discount")
    private BigDecimal discount;
    private String details;
}
