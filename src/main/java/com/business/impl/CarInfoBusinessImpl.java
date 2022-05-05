package com.business.impl;

import com.bo.CarInfoBO;
import com.business.CarInfoBusiness;
import com.business.util.CarInfoBOUtil;
import com.service.ICarInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CarInfoBusinessImpl implements CarInfoBusiness {
    @Autowired
    private ICarInfoService carInfoService;

    @Override
    public List<CarInfoBO> getCarList(int type){
        return CarInfoBOUtil.transferList(carInfoService.getCarList(), type);
    }
}
