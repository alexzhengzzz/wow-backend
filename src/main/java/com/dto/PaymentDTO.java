package com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Deprecated
@Data
public class PaymentDTO {
    @NotBlank(message = "payMethod  is required")
    private String payMethod;
    @NotBlank(message = "cardNum  is required")
    private String cardNum;
    @NotNull(message = "payAmount  is required")
    private BigDecimal payAmount;
    @NotNull(message = "invoiceId  is required")
    private Long invoiceId;
}
