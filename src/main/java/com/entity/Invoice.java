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
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "invoice_id", type = IdType.AUTO)
    private Long invoiceId;
    private Timestamp invoiceDate;
    private BigDecimal amount;
}
