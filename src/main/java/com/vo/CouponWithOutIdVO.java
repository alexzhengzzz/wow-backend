package com.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CouponWithOutIdVO {
    private Timestamp validFrom;
    private Timestamp validTo;
    private Long batchId;
    private Long userId;
    private Boolean isUsed;
}
