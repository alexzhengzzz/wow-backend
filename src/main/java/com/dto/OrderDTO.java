package com.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
@Data
public class OrderDTO {
    private String vinId;
    private Long userId;
    private Long couponId;
    private Integer pickLocId;
    private Integer dropLocId;
    private Timestamp pickDate;
    private Timestamp dropDate;
    private BigDecimal startOdometer;
    private BigDecimal endOdometer;
    private BigDecimal dailyLimitOdometer;
}
