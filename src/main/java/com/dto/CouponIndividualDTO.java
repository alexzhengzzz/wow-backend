package com.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class CouponIndividualDTO {
    @NotNull(message = "userId not null")
    private Long userId;
    @NotNull(message = "batchId not null")
    private Long batchId;
    private Timestamp validFrom;
    private Timestamp validTo;
}
