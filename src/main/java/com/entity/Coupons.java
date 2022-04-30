package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @author zmh
 * @since 2022-04-29
 */
@Data
public class Coupons implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "coupon_id", type = IdType.AUTO)
    private Long couponId;
    private Timestamp validFrom;

    private Timestamp validTo;

    private Long batchId;

    private Long userId;

    private Boolean isUsed;
}
