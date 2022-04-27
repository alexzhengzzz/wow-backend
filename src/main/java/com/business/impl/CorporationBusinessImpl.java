package com.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.CorporationBusiness;
import com.dto.CorpEmployeeDTO;
import com.dto.CorporationDTO;
import com.entity.CorpEmployee;
import com.entity.Corporation;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.CorpEmployeeMapper;
import com.mapper.CorporationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CorporationBusinessImpl implements CorporationBusiness {

    @Autowired
    private CorporationMapper corporationMapper;

    @Autowired
    private CorpEmployeeMapper corpEmployeeMapper;

    @Override
    public void createCorporation(CorporationDTO corporationDTO) {
        // TODO: check if admin

        // check if exist
        QueryWrapper<Corporation> q = new QueryWrapper<>();
        q.eq("company_name", corporationDTO.getCompanyName());
        Corporation corporation = corporationMapper.selectOne(q);
        if (corporation != null) {
            throw GeneralExceptionFactory.create(ErrorCode.INSERT_DB_ERROR);
        }
        corporation = getCorporation(corporationDTO);
        int res = corporationMapper.insert(corporation);
        if (res != 1) {
            throw GeneralExceptionFactory.create(ErrorCode.INSERT_DB_ERROR);
        }
    }

    @Override
    public void deleteCorporation(CorporationDTO corporationDTO) {
        //  delete corporation according to company name
        String companyName = corporationDTO.getCompanyName();
        Corporation corporation = corporationMapper.selectOne(new QueryWrapper<Corporation>().eq("company_name", companyName));
        if (corporation == null) {
            throw GeneralExceptionFactory.create(ErrorCode.UNKNOWN_ERROR);
        }
        int res = corporationMapper.delete(new QueryWrapper<Corporation>().eq("company_name", companyName));
        if (res != 1) {
            throw GeneralExceptionFactory.create(ErrorCode.UNKNOWN_ERROR);
        }
    }

    @Override
    public void addEmployeeToCorporation(CorpEmployeeDTO corpEmployeeDTO) {
        String companyName = corpEmployeeDTO.getCompanyName();
        String employeeId = corpEmployeeDTO.getEmployeeId();

        Corporation co = corporationMapper.selectOne(new QueryWrapper<Corporation>().eq("company_name", companyName));
        if (co == null) {
            throw GeneralExceptionFactory.create(ErrorCode.UNKNOWN_ERROR);
        }
        Long corp_id = co.getCorpId();
        // check if exist in corp_employee
        CorpEmployee ce = corpEmployeeMapper.selectOne(new QueryWrapper<CorpEmployee>().eq("corp_id", corp_id).eq("employee_id", employeeId));
        if (ce != null) {
            throw GeneralExceptionFactory.create(ErrorCode.INSERT_DB_ERROR);
        }
        CorpEmployee corpEmployee = new CorpEmployee();
        corpEmployee.setEmployeeId(employeeId);
        corpEmployee.setCorpId(corp_id);
        int res = corpEmployeeMapper.insert(corpEmployee);
        if (res != 1) {
            throw GeneralExceptionFactory.create(ErrorCode.UNKNOWN_ERROR);
        }
    }


    private Corporation getCorporation(CorporationDTO corporationDTO) {
        Corporation corporation = new Corporation();
        corporation.setCompanyName(corporationDTO.getCompanyName());
        corporation.setRegisterCode(corporationDTO.getRegisterCode());
        return corporation;
    }





}
