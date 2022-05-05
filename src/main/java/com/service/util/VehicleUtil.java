package com.service.util;

import com.entity.Vehicle;
import com.vo.VehicleVO;

import java.util.ArrayList;
import java.util.List;

public class VehicleUtil {
    public static List<VehicleVO> transferList(List<Vehicle> vehicleList){
        List<VehicleVO> vehicleVOList = new ArrayList<>();
        for(Vehicle vehicle:vehicleList){
            vehicleVOList.add(transfer(vehicle));
        }
        return vehicleVOList;
    }

    public static VehicleVO transfer(Vehicle vehicle){
        VehicleVO vehicleVO = new VehicleVO();
        vehicleVO.setModelId(vehicle.getModelId());
        vehicleVO.setClassId(vehicle.getClassId());
        vehicleVO.setOfficeId(vehicle.getOfficeId());
        vehicleVO.setStatus(vehicle.getStatus());
        vehicleVO.setPlateNumber(vehicle.getPlateNumber());
        vehicleVO.setVinId(vehicle.getVinId());
        return vehicleVO;
    }
}
