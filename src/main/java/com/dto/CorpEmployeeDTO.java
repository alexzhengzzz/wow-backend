package com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CorpEmployeeDTO {
    @NotBlank(message = "Please enter companyName name")
    private String companyName;
    @NotBlank(message = "Please enter employeeId name")
    private String employeeId;
}
