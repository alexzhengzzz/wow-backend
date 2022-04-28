package com.service.impl;

import com.dto.VehicleInfoDTO;
import com.entity.VehicleInfo;
import org.springframework.stereotype.Component;

@Component
public class CarInfoUtil {
    public static VehicleInfoDTO transfer(VehicleInfo vehicleInfo){
        VehicleInfoDTO vehicleInfoDTO = new VehicleInfoDTO();
        vehicleInfoDTO.setName(vehicleInfo.getName());
        vehicleInfoDTO.setSeat(vehicleInfo.getSeat());
        vehicleInfoDTO.setPricePerDay(vehicleInfo.getPricePerDay());
        vehicleInfoDTO.setYear(vehicleInfo.getYear());
        return vehicleInfoDTO;
    }
}
