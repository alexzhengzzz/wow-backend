package com.business;

import com.bo.OfficeSelectBO;
import com.bo.OfficeBO;

import java.util.List;

public interface OfficeBusiness {
    List<OfficeSelectBO> getOfficeList();

    List<OfficeBO> getOfficeInfo();
}
