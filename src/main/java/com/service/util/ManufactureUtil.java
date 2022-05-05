package com.service.util;

import com.entity.Manufacture;
import com.vo.ManufactureVO;

import java.util.ArrayList;
import java.util.List;

public class ManufactureUtil {

    public static ManufactureVO transfer(Manufacture manufacture){
        ManufactureVO manufactureVO = new ManufactureVO();
        manufactureVO.setManId(manufacture.getManId());
        manufactureVO.setManName(manufacture.getManName());
        return manufactureVO;
    }

    public static List<ManufactureVO> transferList(List<Manufacture> manufactureList){
        List<ManufactureVO> manufactureVOList = new ArrayList<>();
        for(Manufacture manufacture:manufactureList){
            manufactureVOList.add(transfer(manufacture));
        }
        return manufactureVOList;
    }
}
