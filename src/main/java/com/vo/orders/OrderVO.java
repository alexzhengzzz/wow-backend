package com.vo.orders;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 1. Order id
 * 2. pick/drop location,  pick/drop time, expected_return date
 * 3. basic cost, extra cost
 * 4. Car’s info(name, id, image)
 * 5. invoice ID
 * 6. order status
 */

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
    private BigDecimal basicCost;
    private BigDecimal extraCost;
    private Timestamp expectedDate;
    private Integer orderStatus;
}
