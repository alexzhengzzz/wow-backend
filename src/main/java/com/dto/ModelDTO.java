package com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ModelDTO{
    @NotBlank(message = "manId can not be null")
    private Integer manId;
    @NotBlank(message = "modelName can not be null")
    private String modelName;
    @NotBlank(message = "year can not be null")
    private String year;
    @NotBlank(message = "seatNum can not be null")
    private Integer seatNum;
}
