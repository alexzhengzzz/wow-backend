package com.business.util;

import com.bo.AvailableOfficeBO;
import com.bo.OfficeBO;
import com.entity.Office;
import com.vo.OfficeVO;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class OfficeMaintainBusinessUtil {
    public static List<AvailableOfficeBO> transferToPairs(List<OfficeVO> officeVOList){
        List<AvailableOfficeBO> availableOfficeBOList = new ArrayList<>();
        for(OfficeVO officeVO:officeVOList){
            availableOfficeBOList.add(transferToPair(officeVO));
        }
        return availableOfficeBOList;
    }

    public static AvailableOfficeBO transferToPair(OfficeVO officeVo){
        AvailableOfficeBO availableOfficeBO = new AvailableOfficeBO();
        availableOfficeBO.setOfficeId(officeVo.getOfficeId());
        availableOfficeBO.setOfficeName(officeVo.getName());
        return availableOfficeBO;
    }

    public static List<OfficeBO>    transferToBOs(List<OfficeVO> officeVOList){
        List<OfficeBO> officeBOList =  new ArrayList<>();
        for(OfficeVO  officeVO:officeVOList){
            officeBOList.add(transferToBO(officeVO));
        }
        return officeBOList;
    }

    public static OfficeBO transferToBO(OfficeVO officeVO){
        OfficeBO officeBO = new OfficeBO();
        officeBO.setOfficeId(officeVO.getOfficeId());
        officeBO.setCity(officeVO.getCity());
        officeBO.setCountry(officeVO.getCountry());
        officeBO.setState(officeVO.getState());
        officeBO.setStreet(officeVO.getStreet());
        officeBO.setName(officeVO.getName());
        officeBO.setZipcode(officeVO.getZipcode());
        officeBO.setPhoneNum(officeVO.getPhoneNum());
        return officeBO;
    }
}
