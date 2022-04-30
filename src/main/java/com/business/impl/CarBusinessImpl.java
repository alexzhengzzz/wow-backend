package com.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.business.CarBusiness;
import com.entity.Car;
import com.entity.User;
import com.service.ICarService;
import com.vo.CarVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CarBusinessImpl implements CarBusiness {

    @Autowired
    ICarService carService;
    @Override
    public CarVO getInfo() {
//
        return null;
    }

    private CarVO transfer(Car car){
        CarVO carVO = new CarVO();
        carVO.setClass_id(car.getClass_id());
        carVO.setModel_id(car.getModel_id());
        carVO.setOffice_id(car.getOffice_id());
        carVO.setStatus(car.getStatus());
        carVO.setPlate_number(car.getPlate_number());
        return carVO;
    }

}
