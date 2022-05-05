package com.business;

import com.bo.VehicleSelectBO;
import com.bo.VehicleBO;

import java.util.List;

public interface VehicleBusiness {

    List<VehicleBO> getVehicleInfo();

    List<VehicleSelectBO> getVehicleList();
}
