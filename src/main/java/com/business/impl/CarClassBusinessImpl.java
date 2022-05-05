package com.business.impl;

import com.bo.CarClassSelectBO;
import com.bo.CarClassBO;
import com.business.CarClassBusiness;
import com.business.util.CarClassBOUtil;
import com.service.CarClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class CarClassBusinessImpl implements CarClassBusiness {

    @Autowired
    CarClassService carClassService;

    @Override
    public List<CarClassSelectBO> getClassList() {
        return CarClassBOUtil.transferToBOPairList(carClassService.getCarClassInfo());
    }

    @Override
    public List<CarClassBO> getClassInfo() {
        return CarClassBOUtil.transferToList(carClassService.getCarClassInfo());
    }
}
