package com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VehicleDTO {

    //@NotBlank(message = "vinId not blank")
    private String vinId;
    @NotBlank(message = "plateNumber not blank")
    private String plateNumber;
    @NotBlank(message = "classId not blank")
    private Integer classId;
    @NotBlank(message = "status not blank")
    private String status;
    @NotBlank(message = "officeId not blank")
    private Integer officeId;
    @NotBlank(message = "modelId not blank")
    private Integer modelId;

}
