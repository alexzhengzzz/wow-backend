package com.business.impl;

import com.bo.VehicleSelectBO;
import com.bo.VehicleBO;
import com.business.VehicleBusiness;
import com.business.util.VehicleBOUtil;
import com.dto.VehicleDTO;
import com.service.VehicleService;
import com.service.util.VehicleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class VehicleBusinessImpl implements VehicleBusiness {

    @Autowired
    VehicleService vehicleService;

    @Override
    public List<VehicleBO> getVehicleInfo() {
        return VehicleBOUtil.transferToLists(vehicleService.getVehicleInfo());
    }

    @Override
    public List<VehicleSelectBO> getVehicleList() {
        return VehicleBOUtil.transferToAvBOList(vehicleService.getVehicleInfo());
    }


    @Override
    public boolean createVehicleInfo(VehicleDTO vehicleDTO) {
        return vehicleService.save(VehicleUtil.transferDTO(null, vehicleDTO));
    }

    @Override
    public boolean deleteVehicleById(String vehicleId) {
        return vehicleService.removeById(vehicleId);
    }

    @Override
    public boolean updateVehicle(String vehicleId, VehicleDTO vehicleDTO){
        return vehicleService.updateById(VehicleUtil.transferDTO(vehicleId, vehicleDTO));
    }

//    @Override
//    public boolean updateVehicleStatus(String vehicleId) {
//        return false;
//    }



}
