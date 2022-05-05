package com.business.util;

import com.bo.ManufactureBO;
import com.vo.ManufactureVO;

import java.util.ArrayList;
import java.util.List;

public class ManufactureBOUtil {

    public static ManufactureBO transfer(ManufactureVO manufactureVO) {
        ManufactureBO manufactureBO = new ManufactureBO();
        manufactureBO.setManName(manufactureVO.getManName());
        manufactureBO.setManId(manufactureVO.getManId());
        return manufactureBO;
    }

    public static List<ManufactureBO> transferList(List<ManufactureVO> manufactureVOList){
        List<ManufactureBO> manufactureBOList = new ArrayList<>();
        for(ManufactureVO manufactureVO:manufactureVOList){
            manufactureBOList.add(transfer(manufactureVO));
        }
        return manufactureBOList;
    }
}
