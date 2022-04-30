package com.business.impl;

import com.annotation.PermissionChecker;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.CorporationBusiness;
import com.dto.CorpEmployeeDTO;
import com.dto.CorporationDTO;
import com.entity.CorpEmployee;
import com.entity.Corporation;
import com.entity.User;
import com.enums.Role;
import com.enums.RoleType;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.ICorpEmployeeService;
import com.service.ICorporationService;
import com.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CorporationBusinessImpl implements CorporationBusiness {

    @Autowired
    private ICorporationService corporationService;

    @Autowired
    private ICorpEmployeeService corpEmployeeService;

    @Autowired
    private IUserService userService;


    @Override
    @PermissionChecker(requiredRole = Role.ADMIN)
    public void createCorporation(CorporationDTO corporationDTO) {
        // check if exist
        Corporation corporation = corporationService.getOne(new LambdaQueryWrapper<Corporation>().eq(Corporation::getCompanyName, corporationDTO.getCompanyName()));
        if (corporation != null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_EXISTED_ERROR, "company existed");
        }
        corporation = getCorporation(corporationDTO);
        Boolean isSuccess = corporationService.save(corporation);
        if (isSuccess != true) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
    }

    @Override
    @PermissionChecker(requiredRole = Role.ADMIN)
    public void deleteCorporation(CorporationDTO corporationDTO) {
        //  delete corporation according to company name
        String companyName = corporationDTO.getCompanyName();
        Corporation corporation = corporationService.getOne(new LambdaQueryWrapper<Corporation>().eq(Corporation::getCompanyName, companyName));
        if (corporation == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_NOT_EXISTED_ERROR, "no such company in database");
        }
        boolean isSuccess = corporationService.remove(new LambdaQueryWrapper<>(corporation).eq(Corporation::getCompanyName, companyName));
        if (isSuccess != true) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_DELETE_ERROR);
        }
    }

    @Override
    @PermissionChecker(requiredRole = Role.USER, requiredRoleType = RoleType.CORPORATION)
    public void addEmployeeToCorporation(CorpEmployeeDTO corpEmployeeDTO) {
        String companyName = corpEmployeeDTO.getCompanyName();
        String employeeId = corpEmployeeDTO.getEmployeeId();

        Corporation co = corporationService.getOne(new LambdaQueryWrapper<Corporation>().eq(Corporation::getCompanyName, companyName));
        if (co == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_EXISTED_ERROR, "no such company in database");
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

    @Override
    public List<User> getEmployeeList(String companyName) {
        Corporation corporation = corporationService.getOne(new LambdaQueryWrapper<Corporation>().eq(Corporation::getCompanyName, companyName));
        if (corporation == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_NOT_EXISTED_ERROR);
        }
        Long corp_id = corporation.getCorpId();
        List<CorpEmployee> corpEmployees = corpEmployeeService.list(new LambdaQueryWrapper<CorpEmployee>().eq(CorpEmployee::getCorpId, corp_id));
        List<User> users = new ArrayList<>();
        for (CorpEmployee ce : corpEmployees) {
            User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getEmployeeId, ce.getEmployeeId()));
            if (user!= null) {
                users.add(user);
            }
        }
        return users;
    }


    private Corporation getCorporation(CorporationDTO corporationDTO) {
        Corporation corporation = new Corporation();
        corporation.setCompanyName(corporationDTO.getCompanyName());
        corporation.setRegisterCode(corporationDTO.getRegisterCode());
        return corporation;
    }





}
