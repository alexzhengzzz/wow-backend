package com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IndividualDTO {
    @NotBlank(message = "driverLicence is required")
    private String driverLicence;
    @NotBlank(message = "insuranceCompany is required")
    private String insuranceCompany;
    @NotBlank(message = "insuranceNumber is required")
    private String insuranceNumber;
}
