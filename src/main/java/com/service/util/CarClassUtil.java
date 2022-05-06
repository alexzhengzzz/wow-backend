package com.service.util;

import com.dto.CarClassDTO;
import com.entity.CarClass;
import com.vo.CarClassVO;

import java.util.ArrayList;
import java.util.List;

public class CarClassUtil {
    public static List<CarClassVO>  transferList(List<CarClass> carClassList){
        List<CarClassVO> carClassVOList = new ArrayList<>();
        for(CarClass carClass:carClassList){
            carClassVOList.add(transfer(carClass));
        }
        return carClassVOList;
    }

    public static CarClassVO transfer(CarClass carClass){
        CarClassVO carClassVO = new CarClassVO();
        carClassVO.setClassId(carClass.getClassId());
        carClassVO.setClassType(carClass.getClassType());
        carClassVO.setOverFee(carClass.getOverFee());
        carClassVO.setImageUrl(carClass.getImageUrl());
        carClassVO.setRentalRatePerDay(carClass.getRentalRatePerDay());
        carClassVO.setDailyMileLimit(carClass.getDailyMileLimit());
        return carClassVO;
    }

    public static CarClass transferDTO(Integer carClassId, CarClassDTO carClassDTO){
        CarClass carClass = new CarClass();

        carClass.setClassId(carClassId);

        carClass.setClassType(carClassDTO.getClassType());
        carClass.setImageUrl(carClassDTO.getImageUrl());
        carClass.setOverFee(carClassDTO.getOverFee());
        carClass.setRentalRatePerDay(carClassDTO.getRentalRatePerDay());
        carClass.setDailyMileLimit(carClassDTO.getDailyMileLimit());
        return carClass;
    }
}
