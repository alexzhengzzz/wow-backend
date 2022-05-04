package com.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author alexzhengzzz
 * @date 5/3/22 22:56
 */
@Data
public class OrderCompleteDTO {
    private Long userId;
    private Integer dropLocId;
    private BigDecimal endOdometer;
    private Timestamp dropDate;
}
