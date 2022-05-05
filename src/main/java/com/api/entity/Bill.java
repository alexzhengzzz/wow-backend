package com.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author com
 * @since 2022-05-05
 */
@Data
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long billId;

    private BigDecimal billAmount;

    private String cardNum;

    private Integer status;



}
