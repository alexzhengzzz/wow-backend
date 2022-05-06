package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@TableName("payment_card")
public class PaymentCard implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "card_id", type = IdType.AUTO)
    private Long cardId;
    private Long userId;
    private String cardNum;
    private Timestamp expireDate;
    private String name;
    private String status;
    private String country;
    private String state;
    private String city;
    private String street;
    private String zipcode;
}
