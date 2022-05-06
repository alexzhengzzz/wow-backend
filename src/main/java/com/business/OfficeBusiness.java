package com.business;

import com.bo.OfficeSelectBO;
import com.bo.OfficeBO;
import com.dto.OfficeDTO;

import java.util.List;

public interface OfficeBusiness {
    List<OfficeSelectBO> getOfficeList();

    List<OfficeBO> getOfficeInfo();

    boolean createOfficeInfo(OfficeDTO officeDTO);

    boolean deleteOfficeById(Integer officeId);

    boolean updateOffice(Integer officeId, OfficeDTO officeDTO);
}
