package com.business;

import com.bo.AvailableOfficeBO;
import com.bo.OfficeBO;

import java.util.List;

public interface OfficeMaintainBusiness {
    List<AvailableOfficeBO> getOfficeList();

    List<OfficeBO> getOfficeInformation();
}
