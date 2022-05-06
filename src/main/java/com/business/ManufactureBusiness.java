package com.business;

import com.bo.ManufactureBO;
import com.dto.ManufactureDTO;

import java.util.List;

public interface ManufactureBusiness {

    List<ManufactureBO> getManufactureInfo();

    boolean createManufacture(ManufactureDTO manufactureDTO);

    boolean updateManufacture(Integer manId, ManufactureDTO manufactureDTO);

    boolean deleteManufacture(Integer manId);
}
