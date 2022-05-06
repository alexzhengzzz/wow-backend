package com.business.impl;

import com.annotation.PermissionChecker;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bean.LoginUser;
import com.business.IUserBusiness;
import com.context.ServiceContextHolder;
import com.entity.*;
import com.enums.Role;
import com.enums.RoleType;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.*;
import com.vo.UserCorporationVO;
import com.vo.UserIndividualVO;
import com.vo.UserInfoAddressVO;
import com.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBusinessImpl implements IUserBusiness {
    @Autowired
    private UserService userService;

    @Autowired
    private IUserAddressService userAddressService;

    @Autowired
    private IIndividualService individualService;

    @Autowired
    private ICorporationService corporationService;

    @Autowired
    private ICorpEmployeeService corpEmployeeService;

    @Override
    @PermissionChecker(requiredRole = Role.USER)
    public UserInfoVO getUserInfo() {
        LoginUser loginUser = ServiceContextHolder.getServiceContext().getLoginUser();
        Long userId = loginUser.getUserId();
        RoleType roleType = loginUser.getRoleType();

        UserInfoVO userInfoVO = new UserInfoVO();
        User user = userService.getById(userId);
        if (user == null) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_NOT_FOUND);
        }
        UserAddress userAddress = userAddressService.getById(userId);
        setBaseInfo(userInfoVO, user, userAddress);
        if (roleType == RoleType.INDIVIDUAL) {
            Individual individual = individualService.getById(userId);
            if (individual != null) {
                setIndividualInfo(userInfoVO, individual);
            }
        } else if (roleType == RoleType.CORPORATION) {
            Corporation corporation = corporationService.getById(user.getCompanyId());
            CorpEmployee corpEmployee = corpEmployeeService.getOne(new LambdaQueryWrapper<CorpEmployee>().eq(CorpEmployee::getEmployeeId, user.getEmployeeId()));
            if (corporation != null && corpEmployee != null) {
                setCorporationInfo(userInfoVO, corporation, corpEmployee);
            }
        } else if (roleType == RoleType.ADMIN) {
                return userInfoVO;
        } else {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "no such type");
        }
        return userInfoVO;
    }


    private void setCorporationInfo(UserInfoVO userInfoVO, Corporation corporation, CorpEmployee corpEmployee) {
        UserCorporationVO userCorporationVO = new UserCorporationVO();
        userCorporationVO.setCompanyName(corporation.getCompanyName());
        userCorporationVO.setRegisterCode(corporation.getRegisterCode());
        userCorporationVO.setEmployeeId(corpEmployee.getEmployeeId());
        userInfoVO.setCorporate(userCorporationVO);
    }

    private void setIndividualInfo(UserInfoVO userInfoVO, Individual individual) {
        UserIndividualVO in = new UserIndividualVO();
        in.setInsuranceCompany(individual.getInsuranceCompany());
        in.setInsuranceNumber(individual.getInsuranceNumber());
        in.setDriverLicence(individual.getDriverLicence());
        userInfoVO.setIndividual(in);
    }

    private void setBaseInfo(UserInfoVO userInfoVO, User user, UserAddress userAddress) {
        userInfoVO.setUserId(user.getId());
        userInfoVO.setFname(user.getFname());
        userInfoVO.setLname(user.getLname());
        userInfoVO.setEmail(user.getEmail());
        userInfoVO.setPhoneNum(user.getPhoneNum());
        userInfoVO.setRole_type(user.getRoleType());
        UserInfoAddressVO userInfoAddressVO = new UserInfoAddressVO();
        userInfoAddressVO.setCity(userAddress.getCity());
        userInfoAddressVO.setCountry(userAddress.getCountry());
        userInfoAddressVO.setState(userAddress.getState());
        userInfoAddressVO.setStreet(userAddress.getStreet());
        userInfoAddressVO.setZipcode(userAddress.getZipcode());
        userInfoVO.setUserAddress(userInfoAddressVO);
    }


}
