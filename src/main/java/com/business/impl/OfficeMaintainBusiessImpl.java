package com.business.impl;

import com.bo.AvailableOfficeBO;
import com.bo.OfficeBO;
import com.business.OfficeMaintainBusiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OfficeMaintainBusiessImpl implements OfficeMaintainBusiness {

    @Override
    public List<AvailableOfficeBO> getOfficeList() {
        return null;
    }

    @Override
    public List<OfficeBO> getOfficeInformation() {
        return null;
    }
}
