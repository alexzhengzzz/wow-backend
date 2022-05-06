package com.business;

import com.bo.VehicleSelectBO;
import com.bo.VehicleBO;
import com.dto.OfficeDTO;
import com.dto.VehicleDTO;

import java.util.List;

public interface VehicleBusiness {

    List<VehicleBO> getVehicleInfo();

    List<VehicleSelectBO> getVehicleList();

    boolean createVehicleInfo(VehicleDTO vehicleDTO);

    boolean deleteVehicleById(String vinId);

    boolean updateVehicle(String vinId, VehicleDTO vehicleDTO);


}
