package com.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.CorporationBusiness;
import com.context.ServiceContext;
import com.context.ServiceContextHolder;
import com.dto.CorpEmployeeDTO;
import com.dto.CorporationDTO;
import com.entity.CorpEmployee;
import com.entity.Corporation;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.ICorpEmployeeService;
import com.service.ICorporationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CorporationBusinessImpl implements CorporationBusiness {

    @Autowired
    private ICorporationService corporationService;

    @Autowired
    private ICorpEmployeeService corpEmployeeService;

    @Override
    public void createCorporation(CorporationDTO corporationDTO) {
        ServiceContext serviceContext = ServiceContextHolder.getServiceContext();
        if (serviceContext == null || serviceContext.getAccessToken() == null) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_TOKEN_VERIFY_ERROR);
        }
        String role_type = serviceContext.getAccessToken();
        if (!role_type.equals("0")) {
            throw GeneralExceptionFactory.create(ErrorCode.PERMISSION_DENIED);
        }

        // check if exist
        QueryWrapper<Corporation> q = new QueryWrapper<>();
        q.eq("company_name", corporationDTO.getCompanyName());
        Corporation corporation = corporationService.getOne(q);
        if (corporation != null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_EXISTED_ERROR);
        }
        corporation = getCorporation(corporationDTO);
        Boolean isSuccess = corporationService.save(corporation);
        if (isSuccess != true) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
    }

    @Override
    public void deleteCorporation(CorporationDTO corporationDTO) {
        //  delete corporation according to company name
        String companyName = corporationDTO.getCompanyName();
        Corporation corporation = corporationService.getOne(new LambdaQueryWrapper<Corporation>().eq(Corporation::getCompanyName, companyName));
        if (corporation == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_NOT_EXISTED_ERROR);
        }
        boolean isSuccess = corporationService.remove(new LambdaQueryWrapper<>(corporation).eq(Corporation::getCompanyName, companyName));
        if (isSuccess != true) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_DELETE_ERROR);
        }
    }

    @Override
    public void addEmployeeToCorporation(CorpEmployeeDTO corpEmployeeDTO) {
        String companyName = corpEmployeeDTO.getCompanyName();
        String employeeId = corpEmployeeDTO.getEmployeeId();

        Corporation co = corporationService.getOne(new LambdaQueryWrapper<Corporation>().eq(Corporation::getCompanyName, companyName));
        if (co == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_EXISTED_ERROR);
        }
        Long corp_id = co.getCorpId();
        // check if exist in corp_employee
        CorpEmployee ce = corpEmployeeService.getOne(new LambdaQueryWrapper<CorpEmployee>().eq(CorpEmployee::getCorpId, corp_id).eq(CorpEmployee::getEmployeeId, employeeId));
        if (ce != null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_EXISTED_ERROR);
        }
        CorpEmployee corpEmployee = new CorpEmployee();
        corpEmployee.setEmployeeId(employeeId);
        corpEmployee.setCorpId(corp_id);
        Boolean isSuccess = corpEmployeeService.save(corpEmployee);
        if (isSuccess != true) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
    }


    private Corporation getCorporation(CorporationDTO corporationDTO) {
        Corporation corporation = new Corporation();
        corporation.setCompanyName(corporationDTO.getCompanyName());
        corporation.setRegisterCode(corporationDTO.getRegisterCode());
        return corporation;
    }





}
