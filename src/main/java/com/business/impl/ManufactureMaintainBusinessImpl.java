package com.business.impl;

import com.bo.ManufactureBO;
import com.business.ManufactureBusiness;
import com.business.util.ManufactureBOUtil;
import com.service.IManufactureService;
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
}
