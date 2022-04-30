package com.business.impl;

import com.business.VehicleInfoBusiness;
import com.service.ICarInfoService;
import com.utils.cache.vo.VehicleInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class VehicleInfoBusinessImpl implements VehicleInfoBusiness {
    @Autowired
    private ICarInfoService carInfoService;

    @Override
    public List<VehicleInfoVO> getCarList(){
        return carInfoService.getCarList();
    };
}
