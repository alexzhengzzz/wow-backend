package com.business.util;

import com.bo.OfficeSelectBO;
import com.bo.OfficeBO;
import com.vo.OfficeVO;

import java.util.ArrayList;
import java.util.List;

public class OfficeBOUtil {
    public static List<OfficeSelectBO> transferToPairs(List<OfficeVO> officeVOList){
        List<OfficeSelectBO> officeSelectBOList = new ArrayList<>();
        for(OfficeVO officeVO:officeVOList){
            officeSelectBOList.add(transferToPair(officeVO));
        }
        return officeSelectBOList;
    }

    public static OfficeSelectBO transferToPair(OfficeVO officeVo){
        OfficeSelectBO officeSelectBO = new OfficeSelectBO();
        officeSelectBO.setOfficeId(officeVo.getOfficeId());
        officeSelectBO.setOfficeName(officeVo.getName());
        return officeSelectBO;
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
