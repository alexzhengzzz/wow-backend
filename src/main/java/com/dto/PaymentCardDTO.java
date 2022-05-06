package com.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Data
public class PaymentCardDTO {
    @NotNull(message = "userId can not be empty")
    private Long userId;
    @NotNull(message = "cardNum can not be empty")
    private String cardNum;
    @NotNull(message = "expireDate can not be empty")
    private Timestamp expireDate;
    @NotNull(message = "name can not be empty")
    private String name;

    private String country;
    private String state;
    private String city;
    private String street;
    private String zipcode;
}
