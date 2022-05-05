package com.business.util;

import com.bo.CarClassSelectBO;
import com.bo.CarClassBO;
import com.vo.CarClassVO;

import java.util.ArrayList;
import java.util.List;

public class CarClassBOUtil {
    public static List<CarClassBO> transferToList(List<CarClassVO> carClassVOList){
        List<CarClassBO> carClassBOList = new ArrayList<>();
        for(CarClassVO carClassVO : carClassVOList){
            carClassBOList.add(transferToBO(carClassVO));
        }
        return carClassBOList;
    }

    public static CarClassBO transferToBO(CarClassVO carClassVO){
        CarClassBO carClassBO = new CarClassBO();
        carClassBO.setClassId(carClassVO.getClassId());
        carClassBO.setClassType(carClassVO.getClassType());
        carClassBO.setImageUrl(carClassVO.getImageUrl());
        carClassBO.setOverFee(carClassVO.getOverFee());
        carClassBO.setRentalRatePerDay(carClassVO.getRentalRatePerDay());
        return carClassBO;
    }

    public static CarClassSelectBO transferToBOPair(CarClassVO carClassVO){
        CarClassSelectBO carClassSelectBO = new CarClassSelectBO();
        carClassSelectBO.setClassId(carClassVO.getClassId());
        carClassSelectBO.setClassType(carClassVO.getClassType());
        return carClassSelectBO;
    }

    public static List<CarClassSelectBO> transferToBOPairList(List<CarClassVO> carClassVOList){
        List<CarClassSelectBO> carClassSelectBOList = new ArrayList<>();
        for(CarClassVO carClassVO : carClassVOList){
            carClassSelectBOList.add(transferToBOPair(carClassVO));
        }
        return carClassSelectBOList;
    }

}
