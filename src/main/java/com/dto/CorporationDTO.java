package com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CorporationDTO {
    @NotBlank(message = "Corporation name is required")
    private String companyName;
    @NotBlank(message = "registerCode is required")
    private String registerCode;
}
