package com.business.util;

import com.dto.VehicleInfoDTO;
import com.vo.VehicleInfoVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public static List<VehicleInfoVO> transferList(List<VehicleInfoDTO> vehicleInfoDTOList){
        List<VehicleInfoVO> res = new ArrayList();
        for(VehicleInfoDTO dto:vehicleInfoDTOList){
            res.add(transfer(dto));
        }
        return res;
    }

}
