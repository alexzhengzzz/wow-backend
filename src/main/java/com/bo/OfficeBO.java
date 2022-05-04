package com.bo;

import lombok.Data;

@Data
public class OfficeBO {
    private Integer officeId;

    private String name;

    private String country;

    private String state;

    private String city;

    private String street;

    private String zipcode;

    private String phoneNum;
}
