package com.business.impl;

import com.bo.VehicleSelectBO;
import com.bo.VehicleBO;
import com.business.VehicleBusiness;
import com.business.util.VehicleBOUtil;
import com.service.VehicleService;
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
}
