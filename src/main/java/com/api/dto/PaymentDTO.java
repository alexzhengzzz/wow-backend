package com.api.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author alexzhengzzz
 * @date 5/5/22 12:07
 */
@Data
public class PaymentDTO {
    private String cardNum;

    private String fname;

    private String lname;

    private String country;

    private String state;

    private String city;

    private String zipcode;

    private Integer cvv;

    private String expiredDate;

    private BigDecimal amount;

}
