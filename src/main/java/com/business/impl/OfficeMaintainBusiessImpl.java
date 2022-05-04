package com.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bo.AvailableOfficeBO;
import com.bo.OfficeBO;
import com.business.OfficeMaintainBusiness;
import com.business.util.OfficeMaintainBusinessUtil;
import com.mapper.OfficeMapper;
import com.service.IOfficeMaintainService;
import com.service.OfficeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OfficeMaintainBusiessImpl implements OfficeMaintainBusiness {

    @Autowired
    OfficeService officeMaintainService;


    @Override
    public List<AvailableOfficeBO> getOfficeList() {

       return OfficeMaintainBusinessUtil.transferToPairs(officeMaintainService.getOfficeInfo());
    }

    @Override
    public List<OfficeBO> getOfficeInfo() {
        return OfficeMaintainBusinessUtil.transferToBOs(officeMaintainService.getOfficeInfo());
    }
}
