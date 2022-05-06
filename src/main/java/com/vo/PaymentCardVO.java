package com.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentCardVO {
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
