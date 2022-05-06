package com.service.util;

import com.dto.ManufactureDTO;
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

    public static Manufacture transferDTO( ManufactureDTO manufactureDTO){
        Manufacture manufacture = new Manufacture();
        manufacture.setManName(manufactureDTO.getManName());
        return manufacture;
    }

}
