package com.vo;

import lombok.Data;

@Data
public class UserInfoAddressVO {
    private String country;
    private String state;
    private String street;
    private String city;
    private String zipcode;
}
