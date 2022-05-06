package com.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OfficeDTO {
    @NotNull(message = "name not null")
    private String name;
    @NotNull(message = "country not null")
    private String country;
    @NotNull(message = "state not null")
    private String state;
    @NotNull(message = "city not null")
    private String city;
    @NotNull(message = "street not null")
    private String street;
    @NotNull(message = "zipcode not null")
    private String zipcode;
    @NotNull(message = "phone number not null")
    private String phoneNum;
}
