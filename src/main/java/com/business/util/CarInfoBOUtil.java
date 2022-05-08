package com.business.util;

import com.bo.CarInfoBO;
import com.vo.CarInfoVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarInfoBOUtil {
    public static CarInfoBO transfer(CarInfoVO carInfoVO){
        CarInfoBO carInfoBO = new CarInfoBO();
        carInfoBO.setName(carInfoVO.getName());
        carInfoBO.setSeat(carInfoVO.getSeat());
        carInfoBO.setPricePerDay(carInfoVO.getPricePerDay());
        carInfoBO.setYear(carInfoVO.getYear());
        carInfoBO.setClass_type(carInfoVO.getClass_type());
        carInfoBO.setVin_id(carInfoVO.getVin_id());
        carInfoBO.setImage_url(carInfoVO.getImage_url());
        carInfoBO.setStatus(carInfoVO.getStatus());
        carInfoBO.setLimitMilePerDay(carInfoVO.getLimitMilePerDay());
        carInfoBO.setOverFee(carInfoVO.getOverFee());

        carInfoBO.setManId(carInfoVO.getManId());
        carInfoBO.setManName(carInfoVO.getManName());
        carInfoBO.setOfficeId(carInfoVO.getOfficeId());
        carInfoBO.setOfficeName(carInfoVO.getOfficeName());

        return carInfoBO;
    }

    public static List<CarInfoBO> transferList(List<CarInfoVO> CarInfoVOList, int type){
        List<CarInfoBO> res = new ArrayList();
        for(CarInfoVO vo:CarInfoVOList){
            if(type == 0){
                res.add(transfer(vo));
            }else if(type == 1 && vo.getStatus().equals("I")){
                    res.add(transfer(vo));
            }else if(type == 2 && !vo.getStatus().equals("I")){
                    res.add(transfer(vo));
            }else{
                ;
            }
        }
        return res;
    }

}
