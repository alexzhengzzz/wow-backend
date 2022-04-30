package com.service.util;

import com.entity.VehicleInfo;
import com.vo.VehicleInfoVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarInfoUtil {
    public static VehicleInfoVO transfer(VehicleInfo vehicleInfo){
        VehicleInfoVO vehicleInfoVO = new VehicleInfoVO();
        vehicleInfoVO.setName(vehicleInfo.getName());
        vehicleInfoVO.setSeat(vehicleInfo.getSeat());
        vehicleInfoVO.setPricePerDay(vehicleInfo.getPricePerDay());
        vehicleInfoVO.setYear(vehicleInfo.getYear());
        vehicleInfoVO.setImage_url(vehicleInfo.getImage_url());
        vehicleInfoVO.setVin_id(vehicleInfo.getVin_id());
        return vehicleInfoVO;
    }

    public static List<VehicleInfoVO> transferList(List<VehicleInfo> vehicleInfoList){
        List<VehicleInfoVO> vehicleInfoVOList = new ArrayList<>();
        for(VehicleInfo info:vehicleInfoList){
            vehicleInfoVOList.add(transfer(info));
        }
        return vehicleInfoVOList;
    }
}
