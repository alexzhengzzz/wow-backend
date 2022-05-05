package com.vo;

import lombok.Data;

@Data
public class VehicleVO {
    private String vinId;

    private String plateNumber;

    private Integer classId;

    private String status;

    private Integer officeId;

    private Integer modelId;
}
