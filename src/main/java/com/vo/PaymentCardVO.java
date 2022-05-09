package com.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentCardVO {
    private Long cardId;
    private Long userId;
    private String cardNum;
    private String expireDate;
    private String lname;
    private String fname;
    private String status;
    private String country;
    private String state;
    private String city;
    private String street;
    private String zipcode;
}
