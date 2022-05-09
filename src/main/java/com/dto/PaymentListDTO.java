package com.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentListDTO {

    int size;

    List<PaymentUnitDTO> paymentUnitDTOList;
}
