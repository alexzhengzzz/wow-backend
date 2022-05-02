package com.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CouponIndividualDTO {
    private Long userId;
    private Long batchId;
    private Timestamp validFrom;
    private Timestamp validTo;
}
