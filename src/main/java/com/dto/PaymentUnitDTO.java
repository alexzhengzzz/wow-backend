package com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PaymentUnitDTO {
    //@NotBlank(message = "cardId  is required")
    private Long cardId;
    //private String cardNum;
    //@NotNull(message = "payAmount  is required")
    private BigDecimal payAmount;
//    @NotNull(message = "invoiceId  is required")
//    private Long invoiceId;
    private PaymentCardDTO paymentCardDTO;
}
