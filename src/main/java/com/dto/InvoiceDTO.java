package com.dto;

import lombok.Data;

import java.util.List;

@Deprecated
@Data
public class InvoiceDTO {
    List<PaymentUnitDTO>  paymentUnitDTOList;
}
