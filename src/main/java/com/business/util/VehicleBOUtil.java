package com.business.util;

import com.bo.VehicleSelectBO;
import com.bo.VehicleBO;
import com.vo.VehicleVO;

import java.util.ArrayList;
import java.util.List;

public class VehicleBOUtil {
    public static List<VehicleBO> transferToLists(List<VehicleVO> vehicleVOList){
        List<VehicleBO> vehicleBOList = new ArrayList<>();
        for(VehicleVO  vehicleVO:vehicleVOList){
            vehicleBOList.add(transferToBO(vehicleVO));
        }
        return vehicleBOList;
    }

    public static VehicleBO transferToBO(VehicleVO vehicleVO){
        VehicleBO vehicleBO = new VehicleBO();
        vehicleBO.setOfficeId(vehicleVO.getOfficeId());
        vehicleBO.setStatus(vehicleVO.getStatus());
        vehicleBO.setClassId(vehicleVO.getClassId());
        vehicleBO.setModelId(vehicleVO.getModelId());
        vehicleBO.setPlateNumber(vehicleVO.getPlateNumber());
        vehicleBO.setVinId(vehicleVO.getVinId());
        return vehicleBO;
    }

    public static VehicleSelectBO transferToAvBO(VehicleVO vehicleVO){
        VehicleSelectBO vehicleSelectBO = new VehicleSelectBO();
        vehicleSelectBO.setPlateNumber(vehicleVO.getPlateNumber());
        vehicleSelectBO.setVinId(vehicleVO.getVinId());
        return vehicleSelectBO;
    }

    public static List<VehicleSelectBO> transferToAvBOList(List<VehicleVO> vehicleVOList){
        List<VehicleSelectBO> vehicleSelectBOList = new ArrayList<>();
        for(VehicleVO vehicleVO:vehicleVOList){
            vehicleSelectBOList.add(transferToAvBO(vehicleVO));
        }
        return vehicleSelectBOList;
    }
}
