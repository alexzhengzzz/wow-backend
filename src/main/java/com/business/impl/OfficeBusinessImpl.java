package com.business.impl;

import com.bo.OfficeSelectBO;
import com.bo.OfficeBO;
import com.business.OfficeBusiness;
import com.business.util.OfficeBOUtil;
import com.service.OfficeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OfficeBusinessImpl implements OfficeBusiness {

    @Autowired
    OfficeService officeMaintainService;


    @Override
    public List<OfficeSelectBO> getOfficeList() {

       return OfficeBOUtil.transferToPairs(officeMaintainService.getOfficeInfo());
    }

    @Override
    public List<OfficeBO> getOfficeInfo() {
        return OfficeBOUtil.transferToBOs(officeMaintainService.getOfficeInfo());
    }
}
