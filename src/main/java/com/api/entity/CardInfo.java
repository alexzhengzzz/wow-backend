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
public class CardInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String cardNum;

    private String fname;

    private String lname;

    private String country;

    private String state;

    private String city;

    private String zipcode;

    private Integer cvv;

    private String expiredDate;

    private BigDecimal balance;


}
