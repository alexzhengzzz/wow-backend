package com.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class CouponIndividualDTO {
    private Long userId;
    private Double discount;
    private Timestamp validFrom;
    private Timestamp validTo;
}
