package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author zmh
 * @since 2022-04-30
 */
@TableName("coupons_batch")
@Data
public class CouponsBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "batch_id", type = IdType.AUTO)
    private Long batchId;

    private Integer stock;

    private BigDecimal discount;

    private Character couponType;
    private String details;

}
