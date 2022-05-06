package com.business;

import com.bo.CarClassSelectBO;
import com.bo.CarClassBO;
import com.dto.CarClassDTO;

import java.util.List;

public interface CarClassBusiness {

    List<CarClassSelectBO> getClassList();

    List<CarClassBO> getClassInfo();

    boolean updateCarClassById(Integer carClassId, CarClassDTO carClassDTO);


    boolean deleteCarClassById(Integer carClassId);

    boolean createCarClassById(CarClassDTO carClassDTO);
}
