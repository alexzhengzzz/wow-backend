package com.business.impl;

import com.dto.VehicleInfoDTO;
import com.entity.VehicleInfo;
import com.vo.VehicleInfoVO;
import org.springframework.stereotype.Component;

@Component
public class CarInfoBusinessUtil {
    public static VehicleInfoVO transfer(VehicleInfoDTO vehicleInfoDTO){
        VehicleInfoVO vehicleInfoVo = new VehicleInfoVO();
        vehicleInfoVo.setName(vehicleInfoDTO.getName());
        vehicleInfoVo.setSeat(vehicleInfoDTO.getSeat());
        vehicleInfoVo.setPricePerDay(vehicleInfoDTO.getPricePerDay());
        vehicleInfoVo.setYear(vehicleInfoDTO.getYear());
        return vehicleInfoVo;
    }
}
