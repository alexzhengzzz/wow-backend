package com.service.util;

import com.entity.Office;
import com.vo.OfficeVO;

import java.util.ArrayList;
import java.util.List;

public class OfficeUtil {

    public static OfficeVO transfer(Office office){
        OfficeVO officeVO = new OfficeVO();
        officeVO.setOfficeId(office.getOfficeId());
        officeVO.setCity(office.getCity());
        officeVO.setName(office.getName());
        officeVO.setCountry(office.getCountry());
        officeVO.setState(office.getState());
        officeVO.setStreet(office.getStreet());
        officeVO.setPhoneNum(office.getPhoneNum());
        officeVO.setZipcode(office.getZipcode());
        return officeVO;
    }

    public static List<OfficeVO> transferList(List<Office> officeList){
        List<OfficeVO> officeVOList = new ArrayList<>();
        for(Office office:officeList){
            officeVOList.add(transfer(office));
        }
        return officeVOList;
    }
}
