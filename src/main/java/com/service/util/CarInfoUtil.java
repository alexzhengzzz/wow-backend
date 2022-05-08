package com.service.util;

import com.entity.info.CarInfo;
import com.vo.CarInfoVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarInfoUtil {
    public static CarInfoVO transfer(CarInfo carInfo){
        CarInfoVO carInfoVO = new CarInfoVO();
        carInfoVO.setName(carInfo.getName());
        carInfoVO.setSeat(carInfo.getSeat());
        carInfoVO.setPricePerDay(carInfo.getPricePerDay());
        carInfoVO.setYear(carInfo.getYear());
        carInfoVO.setImage_url(carInfo.getImage_url());
        carInfoVO.setVin_id(carInfo.getVin_id());
        carInfoVO.setClass_type(carInfo.getClass_type());
        carInfoVO.setStatus(carInfo.getStatus());
        carInfoVO.setLimitMilePerDay(carInfo.getLimitMilePerDay());
        carInfoVO.setOverFee(carInfo.getOverFee());

        carInfoVO.setManId(carInfo.getManId());
        carInfoVO.setManName(carInfo.getManName());
        carInfoVO.setOfficeId(carInfo.getOfficeId());
        carInfoVO.setOfficeName(carInfo.getOfficeName());

        return carInfoVO;
    }

    public static List<CarInfoVO> transferList(List<CarInfo> carInfoList){
        List<CarInfoVO> carInfoVOList = new ArrayList<>();
        for(CarInfo info: carInfoList){
            carInfoVOList.add(transfer(info));
        }
        return carInfoVOList;
    }
}
