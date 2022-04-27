package com.dto;

import lombok.Data;

@Data
public class RegisterUserAddressDTO {
    private String country;
    private String state;
    private String street;
    private String city;
    private String zipcode;
}
