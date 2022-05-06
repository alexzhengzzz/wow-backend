package com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class OrderDTO {
    @NotBlank(message = "vinId can not be empty")
    private String vinId;
    @NotNull(message = "userId can not be empty")
    private Long userId;
    private Long couponId;
    @NotNull(message = "pickLocId can not be empty")
    private Integer pickLocId;
    private Integer dropLocId;
    @NotNull(message = "pickDate can not be empty")
    private Timestamp pickDate;
    private Timestamp dropDate;
    @NotNull(message = "startOdometer can not be empty")
    private BigDecimal startOdometer;
    private BigDecimal endOdometer;
    @NotNull(message = "dailyLimitOdometer can not be empty")
    private BigDecimal dailyLimitOdometer;
    private Timestamp expectedDate;
}
