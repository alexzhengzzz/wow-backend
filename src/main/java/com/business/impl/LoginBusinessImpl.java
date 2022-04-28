package com.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bean.LoginUser;
import com.business.LoginBusiness;
import com.dto.*;
import com.entity.*;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.ICorpEmployeeService;
import com.service.ICorporationService;
import com.service.IIndividualService;
import com.service.IUserAddressService;
import com.service.impl.UserServiceImpl;
import com.utils.cache.IGlobalCache;
import com.utils.cache.JWTUtils;
import com.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Component
@Slf4j
public class LoginBusinessImpl implements LoginBusiness {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private IUserAddressService userAddressService;

    @Autowired
    private IIndividualService iIndividualService;

    @Autowired
    private ICorporationService corporationService;

    @Autowired
    private ICorpEmployeeService corpEmployeeService;

    @Autowired
    private IGlobalCache iGlobalCache;

    @Override
    public UserVO login(LoginDTO loginDTO) {
        // 1. 查找该用户
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, loginDTO.getEmail()), false);
        // 2. 用户不存在
        if (user == null) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_NOT_FOUND, loginDTO.getEmail());
        }
        // 3. 密码校验
        String loginMD5pass = encryptPass(loginDTO.getPassword());
        log.debug(loginMD5pass);
        if (!user.getPassword().equals(loginMD5pass)) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_PASSWORD_WRONG, loginDTO.getEmail());
        }
        // 4. UserVO
        String token = JWTUtils.createToken(user.getEmail());
        LoginUser loginUser = getLoginUser(user);
        iGlobalCache.set("login:"+user.getEmail(), loginUser);
        UserVO userVo = getUserVO(user, token);
        return userVo;
    }

    private LoginUser getLoginUser(User user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setEmail(user.getEmail());
        loginUser.setUserId(user.getId());
        loginUser.setRole(user.getRoleType());
        return loginUser;
    }

    @Override
    @Transactional
    public UserVO register(RegisterDTO registerDTO) throws RuntimeException {
        // 1. check info
        _checkUserInfo(registerDTO);
        // 2. create new user
        User newUser = new User();
        setNewUser(newUser, registerDTO);
        boolean isSuccess = userService.save(newUser);
        if (isSuccess != true) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
        Long userId = newUser.getId();

        // 3. create new user_address
        RegisterUserAddressDTO userAddressDTO =  registerDTO.getUserAddress();
        UserAddress userAddress = getUserAddress(userAddressDTO, userId);
        isSuccess = userAddressService.save(userAddress);
        if (isSuccess != true) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }

        // 3. user role type: 0 admin 1: individual 2: corp
        Character role_type = registerDTO.getRole_type();
        switch (role_type) {
            case '0': {
                break;
            }
            case '1': {
                RegisterIndividualDTO registerIndividualDTO = registerDTO.getIndividual();
                Individual in = getIndividual(registerIndividualDTO, userId);
                iIndividualService.save(in);
                break;
            }
            case '2': {
                RegisterCorporDTO registerCorporDTO = registerDTO.getCorporate();
                Long corp_id = _checkAndGetCorpId(registerDTO);
                newUser.setCompanyId(corp_id);
                newUser.setEmployeeId(registerCorporDTO.getEmployeeId());
                userService.updateById(newUser);
                break;
            }
        }
        // get user token
        String token = JWTUtils.createToken(newUser.getEmail());
        UserVO userVo = getUserVO(newUser, token);
        return userVo;
    }

    private Individual getIndividual(RegisterIndividualDTO registerIndividualDTO, Long userId) {
        Individual in = new Individual();
        in.setUserId(userId);
        in.setDriverLicence(registerIndividualDTO.getDriverLicence());
        in.setInsuranceNumber(registerIndividualDTO.getInsuranceNumber());
        in.setInsuranceCompany(registerIndividualDTO.getInsuranceCompany());
        return in;
    }

    private UserAddress getUserAddress(RegisterUserAddressDTO userAddressDTO, Long userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setCountry(userAddressDTO.getCountry());
        userAddress.setCity(userAddressDTO.getCity());
        userAddress.setState(userAddressDTO.getStreet());
        userAddress.setZipcode(userAddressDTO.getZipcode());
        userAddress.setStreet(userAddressDTO.getStreet());
        return userAddress;
    }

    private Long _checkAndGetCorpId(RegisterDTO registerDTO) {
        RegisterCorporDTO reco = registerDTO.getCorporate();
        String employee_id = reco.getEmployeeId();
        String company_name = reco.getCompanyName();
        Corporation co = corporationService.getOne(new LambdaQueryWrapper<Corporation>().eq(Corporation::getCompanyName, company_name));
        if (co == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_NOT_EXISTED_ERROR, company_name);
        }
        Long corp_id = co.getCorpId();
        CorpEmployee res = corpEmployeeService.getOne(new LambdaQueryWrapper<CorpEmployee>().eq(CorpEmployee::getEmployeeId, employee_id).eq(CorpEmployee::getCorpId, corp_id));
        if (res == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_NOT_EXISTED_ERROR, "corp_id: " + corp_id + "employee_id: " + employee_id);
        }
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getCompanyId, corp_id).eq(User::getEmployeeId, employee_id));
        if (user != null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_NOT_EXISTED_ERROR);
        }
        return corp_id;

    }

    private void _checkUserInfo(RegisterDTO registerDTO) {
        // 1. check field
        isIllegal(registerDTO);
        // 2. check user existed
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, registerDTO.getEmail()), false);
        if (user != null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_NOT_EXISTED_ERROR, registerDTO.getEmail(), registerDTO.getFname() + " " + registerDTO.getLname());
        }
    }


    private void isIllegal(RegisterDTO registerDTO) {
        String email = registerDTO.getEmail();
        String fname = registerDTO.getFname();
        String lname = registerDTO.getLname();
        String password = registerDTO.getPassword();
        Integer role_type = Integer.valueOf(registerDTO.getRole_type());
        if (StringUtils.isBlank(email) || StringUtils.isBlank(fname) || StringUtils.isBlank(lname) || StringUtils.isBlank(password) || (role_type > 2 && role_type < 0) ) {
            throw GeneralExceptionFactory.create(ErrorCode.ILLEGAL_DATA);
        }
    }

    private User setNewUser(User user, RegisterDTO registerDTO) {
        user.setPassword(encryptPass(registerDTO.getPassword()));
        user.setFname(registerDTO.getFname());
        user.setLname(registerDTO.getLname());
        user.setEmail(registerDTO.getEmail());
        user.setRoleType(registerDTO.getRole_type());
        return user;
    }


    private UserVO getUserVO(User user, String token) {
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setFname(user.getFname());
        userVO.setLname(user.getLname());
        userVO.setToken(token);
        return userVO;
    }

    private String encryptPass(String pass) {
        return DigestUtils.md5DigestAsHex(pass.getBytes());
    }


}

