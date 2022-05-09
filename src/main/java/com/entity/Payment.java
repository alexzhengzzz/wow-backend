package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * <p>
 * 
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
@Data
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "payment_id", type = IdType.AUTO)
    private Long paymentId;
    //private String payMethod;
    private Long cardId;
    //private String cardNum;
    private BigDecimal payAmount;
    private Long invoiceId;
    private Timestamp payDate;
}
