package com.business.impl;

import com.bo.CarClassSelectBO;
import com.bo.CarClassBO;
import com.business.CarClassBusiness;
import com.business.util.CarClassBOUtil;
import com.dto.CarClassDTO;
import com.service.ICarClassService;
import com.service.util.CarClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class CarClassBusinessImpl implements CarClassBusiness {

    @Autowired
    ICarClassService carClassService;

    @Override
    public List<CarClassSelectBO> getClassList() {
        return CarClassBOUtil.transferToBOPairList(carClassService.getCarClassInfo());
    }

    @Override
    public List<CarClassBO> getClassInfo() {
        return CarClassBOUtil.transferToList(carClassService.getCarClassInfo());
    }

    @Override
    public boolean updateCarClassById(Integer carClassId, CarClassDTO carClassDTO) {
        return carClassService.updateById(CarClassUtil.transferDTO(carClassId, carClassDTO));
    }

    @Override
    public boolean deleteCarClassById(Integer carClassId) {
        return carClassService.removeById(carClassId);
    }

    @Override
    public boolean createCarClassById(CarClassDTO carClassDTO) {
        return carClassService.save(CarClassUtil.transferDTO(null, carClassDTO));
    }
}
