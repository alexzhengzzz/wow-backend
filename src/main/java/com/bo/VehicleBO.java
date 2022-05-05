package com.bo;

import lombok.Data;

@Data
public class VehicleBO {
    private String vinId;

    private String plateNumber;

    private Integer classId;

    private String status;

    private Integer officeId;

    private Integer modelId;
}
