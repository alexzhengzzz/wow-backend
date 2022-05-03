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
 * @since 2022-05-02
 */
@TableName("yzm_car_class")
@Data
public class CarClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "class_id", type = IdType.AUTO)
    private Integer classId;

    private String classType;

    private String imageUrl;

    private BigDecimal rentalRatePerDay;

    private BigDecimal overFee;



}
