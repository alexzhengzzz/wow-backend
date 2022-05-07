package com.dto;
import lombok.Data;

@Data
public class CarInfoDTO {
    // manufacture
    //private  ManufactureDTO manufactureDTO;
    private Integer manId;
    private String manName;
    private  Integer carClassId;
    private  Integer officeId;
    private  Integer modelId;
    private  String vehicleId;
    private  String plateNumber;

    // carClass
    private  CarClassDTO carClassDTO;
    // office
    private  OfficeDTO officeDTO;
    // model
    private  ModelDTO modelDTO;


    // vehicle
    //private  VehicleDTO vehicleDTO;

}
