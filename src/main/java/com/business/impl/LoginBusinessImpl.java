package com.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.LoginBusiness;
import com.dto.*;
import com.entity.*;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.*;
import com.service.impl.UserServiceImpl;
import com.utils.cache.JWTUtils;
import com.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
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
    private UserAddressMapper userAddressMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private CorporationMapper corporationMapper;

    @Autowired
    private CorpEmployeeMapper corpEmployeeMapper;

    @Override
    public UserVO login(LoginDTO loginDTO) {
        // 1. 查找该用户
        User user = userService.getOne(new QueryWrapper<User>().eq("email", loginDTO.getEmail()), false);
        // 2. 用户不存在
        if (user == null) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_NOT_FOUND, loginDTO.getEmail());
        }
        // 3. 密码校验
        String loginMD5pass = encryptPass(loginDTO.getPassword());
        if (!user.getPassword().equals(loginMD5pass)) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_PASSWORD_WRONG, loginDTO.getEmail());
        }
        // 4. UserVO
        String token = JWTUtils.createToken(user.getEmail());
        UserVO userVo = getUserVO(user, token);
        return userVo;
    }

    @Override
    @Transactional
    public UserVO register(RegisterDTO registerDTO) throws RuntimeException {
        // 1. check info
        _checkUserInfo(registerDTO);

        // 2. create new user
        User newUser = new User();
        setNewUser(newUser, registerDTO);
        int res = userMapper.insert(newUser);
        if (res != 1) {
            throw GeneralExceptionFactory.create(ErrorCode.INSERT_DB_ERROR);
        }
        Long userId = newUser.getId();

        // 3. create new user_address
        RegisterUserAddressDTO userAddressDTO =  registerDTO.getUserAddress();
        UserAddress userAddress = getUserAddress(userAddressDTO, userId);
        res = userAddressMapper.insert(userAddress);
        if (res != 1) {
            throw GeneralExceptionFactory.create(ErrorCode.INSERT_DB_ERROR);
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
                individualMapper.insert(in);
                break;
            }
            case '2': {
                RegisterCorporDTO registerCorporDTO = registerDTO.getCorporate();
                Long corp_id = _checkAndGetCorpId(registerDTO);
                newUser.setCompanyId(corp_id);
                newUser.setEmployeeId(registerCorporDTO.getEmployeeId());
                userMapper.updateById(newUser);
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

    private Long _checkAndGetCorpId(@NotNull RegisterDTO registerDTO) {
        RegisterCorporDTO reco = registerDTO.getCorporate();
        String employee_id = reco.getEmployeeId();
        String company_name = reco.getCompanyName();
        QueryWrapper<Corporation> q = new QueryWrapper<>();
        q.eq("company_name", company_name);
        Corporation co = corporationMapper.selectOne(q);
        if (co == null) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_INFO_ILLEGAL, company_name);
        }
        Long id = co.getCorpId();
        CorpEmployee res = corpEmployeeMapper.selectOne(new QueryWrapper<CorpEmployee>().eq("corp_id", id).eq("employee_id", employee_id));
        if (res == null) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_INFO_ILLEGAL, "corp_id: " + id + "employee_id: " + employee_id);
        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("company_id", id).eq("employee_id", employee_id));
        if (user != null) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_INFO_EXISTED);
        }
        return id;

    }

    private Corporation setNewCorp(RegisterCorporDTO corp) {
        Corporation corporation = new Corporation();
        corporation.setCompanyName(corp.getCompanyName());
        corporation.setRegisterCode(corp.getRegisterCode());
        return corporation;
    }

    private void _checkUserInfo(RegisterDTO registerDTO) {
        // 1. check field
        isIllegal(registerDTO);

        // 2. check user existed
        User user = userService.getOne(getQueryWrapperUser(registerDTO), false);
        if (user != null) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_INFO_EXISTED, registerDTO.getEmail(), registerDTO.getFname() + " " + registerDTO.getLname());
        }
    }


    private void isIllegal(RegisterDTO registerDTO) {
        String email = registerDTO.getEmail();
        String fname = registerDTO.getFname();
        String lname = registerDTO.getLname();
        String password = registerDTO.getPassword();
        Integer role_type = Integer.valueOf(registerDTO.getRole_type());
        if (StringUtils.isBlank(email) || StringUtils.isBlank(fname) || StringUtils.isBlank(lname) || StringUtils.isBlank(password) || (role_type > 2 && role_type < 0) ) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_INFO_ILLEGAL);
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

    private QueryWrapper<User> getQueryWrapperUser(RegisterDTO registerDTO) {
        QueryWrapper<User> q = new QueryWrapper<>();
        q.eq("email", registerDTO.getEmail());
        return q;
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

