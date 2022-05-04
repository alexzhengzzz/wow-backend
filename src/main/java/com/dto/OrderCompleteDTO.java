package com.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author alexzhengzzz
 * @date 5/3/22 22:56
 */
@Data
public class OrderCompleteDTO {
    @NotNull(message = "userId not null")
    private Long userId;
    @NotNull(message = "dropLocId not null")
    private Integer dropLocId;
    @NotNull(message = "endOdometer not null")
    private BigDecimal endOdometer;
    @NotNull(message = "dropDate not null")
    private Timestamp dropDate;
}
