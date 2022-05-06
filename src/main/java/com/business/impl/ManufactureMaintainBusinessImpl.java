package com.business.impl;

import com.bo.ManufactureBO;
import com.business.ManufactureBusiness;
import com.business.util.ManufactureBOUtil;
import com.dto.ManufactureDTO;
import com.entity.Manufacture;
import com.service.IManufactureService;
import com.service.util.ManufactureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ManufactureMaintainBusinessImpl implements ManufactureBusiness {

    @Autowired
    IManufactureService manufactureService;

    @Override
    public List<ManufactureBO> getManufactureInfo() {
        return ManufactureBOUtil.transferList(manufactureService.getManufactureInfo());
    }

    @Override
    public boolean createManufacture(ManufactureDTO manufactureDTO) {
        manufactureService.save(ManufactureUtil.transferDTO(manufactureDTO));
        return true;
    }

    @Override
    public boolean updateManufacture(Integer manId, ManufactureDTO manufactureDTO){
        Manufacture manufacture = ManufactureUtil.transferDTO(manufactureDTO);
        manufacture.setManId(manId);
        return manufactureService.updateById(manufacture);
    }

    @Override
    public boolean deleteManufacture(Integer manId) {
        return manufactureService.removeById(manId);
    }
}
