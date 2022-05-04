package com.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class OrderVO {
    private Long orderId;
    private String vinId;
    private Long userId;
    private Long invoiceId;
    private Long couponId;
    private Integer pickLocId;
    private Integer dropLocId;
    private Timestamp pickDate;
    private Timestamp dropDate;
    private BigDecimal startOdometer;
    private BigDecimal endOdometer;
    private BigDecimal dailyLimitOdometer;
}
