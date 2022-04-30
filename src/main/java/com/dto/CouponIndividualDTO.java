package com.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class CouponIndividualDTO {
    private Long userId;
    private Long batchId;
    private Timestamp validFrom;
    private Timestamp validTo;
}
