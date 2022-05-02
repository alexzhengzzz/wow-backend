package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author com
 * @since 2022-05-02
 */
@TableName("rental_order")
@Data
public class RentalOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.AUTO)
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
