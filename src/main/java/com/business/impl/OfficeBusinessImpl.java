package com.business.impl;

import com.bo.OfficeSelectBO;
import com.bo.OfficeBO;
import com.business.OfficeBusiness;
import com.business.util.OfficeBOUtil;
import com.dto.OfficeDTO;
import com.entity.Office;
import com.service.OfficeService;
import com.service.util.OfficeUtil;
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

    @Override
    public boolean createOfficeInfo(OfficeDTO officeDTO) {
        return officeMaintainService.save(OfficeUtil.transferDTO(officeDTO));
    }

    @Override
    public boolean deleteOfficeById(Integer officeId) {
        return officeMaintainService.removeById(officeId);
    }

    @Override
    public boolean updateOffice(Integer officeId, OfficeDTO officeDTO){
        Office office = OfficeUtil.transferDTO(officeDTO);
        office.setOfficeId(officeId);
        return officeMaintainService.updateById(office);
    }
}
