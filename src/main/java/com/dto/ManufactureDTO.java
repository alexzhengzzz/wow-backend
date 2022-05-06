package com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ManufactureDTO {
    @NotBlank(message = "Name can not be null")
    private String manName;
}
