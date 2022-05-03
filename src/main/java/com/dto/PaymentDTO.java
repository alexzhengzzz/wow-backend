package com.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDTO {
    private String payMethod;
    private String cardNum;
    private BigDecimal payAmount;
    private Long invoiceId;
}
