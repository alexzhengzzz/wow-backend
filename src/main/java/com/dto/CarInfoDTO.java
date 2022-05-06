package com.dto;

import com.entity.Vehicle;
import lombok.Data;

@Data
public class CarInfoDTO {
    // manufacture
    //private  ManufactureDTO manufactureDTO;
    private Integer manId;
    private String manName;

    // carClass
    private  CarClassDTO carClassDTO;
    private  Integer carClassId;

    // office
    private  OfficeDTO officeDTO;
    private  Integer officeId;

    // model
    private  ModelDTO modelDTO;
    private  Integer modelId;

    // vehicle
    //private  VehicleDTO vehicleDTO;
    private  String vehicleId;
    private  String plateNumber;
}
