package com.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bean.LoginUser;
import com.business.LoginBusiness;
import com.context.ServiceContextHolder;
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
import com.utils.cache.RoleUtils;
import com.vo.TokenContent;
import com.vo.TokenInfoVO;
import com.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * @author alexzhengzzz
 * @create 2019-08-12 11:24:00
 * @description login business
 */
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

    private final static String TOKEN_KEY_HEADER = "login:";

    @Override
    public UserVO login(LoginDTO loginDTO) {
        // 1. query user
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, loginDTO.getEmail()), false);
        // 2. no such user
        if (user == null) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_NOT_FOUND, "null");
        }
        // 3. password check
        String loginMD5pass = encryptPass(loginDTO.getPassword());
        if (!user.getPassword().equals(loginMD5pass)) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_PASSWORD_WRONG);
        }
        // 4. redis = "login:{email}" : loginUser
        return createAndSetTokenToCache(user);
    }

    @Override
    public TokenInfoVO refreshToken(String token) {
        TokenContent tokenContent = JWTUtils.resolveToken(token);
        String sub = tokenContent.getSubject();
        String redisKey = TOKEN_KEY_HEADER + sub;
        LoginUser loginUser = (LoginUser) iGlobalCache.get(redisKey);
        return convertToTokenInfoVO(loginUser, tokenContent);
    }

    public static TokenInfoVO convertToTokenInfoVO(LoginUser item, TokenContent tokenContent) {
        TokenInfoVO result = new TokenInfoVO();
        result.setLoginUser(item);
        result.setToken(tokenContent);
        return result;
    }

    private LoginUser getLoginUser(User user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setEmail(user.getEmail());
        loginUser.setUserId(user.getId());
        loginUser.setRole(RoleUtils.getRole(user));
        loginUser.setRoleType(RoleUtils.getRoleType(user));
        return loginUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO register(RegisterDTO registerDTO) throws RuntimeException {
        // 1. check info
        checkUserInfo(registerDTO);
        // 2. create new user
        User newUser = new User();
        setNewUser(newUser, registerDTO);
        boolean isSuccess = userService.save(newUser);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
        Long userId = newUser.getId();

        // 3. create new user_address
        RegisterUserAddressDTO userAddressDTO = registerDTO.getUserAddress();
        UserAddress userAddress = getUserAddress(userAddressDTO, userId);
        isSuccess = userAddressService.save(userAddress);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }

        // 3. user role type: 0 admin 1: individual 2: corp
        Character roleType = registerDTO.getRole_type();
        switch (roleType) {
            case '0': {
                break;
            }
            case '1': {
                RegisterIndividualDTO registerIndividualDTO = registerDTO.getIndividual();
                Individual in = getIndividual(registerIndividualDTO, userId);
                iIndividualService.save(in);
                break;
            }
            // check corporation existed
            case '2': {
                RegisterCorporDTO registerCorporDTO = registerDTO.getCorporate();
                Long corpId = checkAndGetCorpId(registerDTO);
                newUser.setCompanyId(corpId);
                newUser.setEmployeeId(registerCorporDTO.getEmployeeId());
                userService.updateById(newUser);
                break;
            }
            default: {
                throw GeneralExceptionFactory.create(ErrorCode.USER_INFO_ILLEGAL, "role type illegal");
            }
        }
        // get user token
        return createAndSetTokenToCache(newUser);
    }

    @NotNull
    private UserVO createAndSetTokenToCache(User newUser) {
        String token = JWTUtils.createToken(newUser.getEmail());
        LoginUser loginUser = getLoginUser(newUser);
        ServiceContextHolder.getServiceContext().setLoginUser(loginUser);
        iGlobalCache.set("login:" + newUser.getEmail(), loginUser);
        return getUserVO(newUser, token);
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

    private Long checkAndGetCorpId(RegisterDTO registerDTO) {
        RegisterCorporDTO reco = registerDTO.getCorporate();
        String employeeId = reco.getEmployeeId();
        String companyName = reco.getCompanyName();
        Corporation co = corporationService.getOne(new LambdaQueryWrapper<Corporation>().eq(Corporation::getCompanyName, companyName));
        if (co == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_NOT_EXISTED_ERROR, "corporation not found");
        }
        Long corpId = co.getCorpId();
        CorpEmployee res = corpEmployeeService.getOne(new LambdaQueryWrapper<CorpEmployee>().eq(CorpEmployee::getEmployeeId, employeeId).eq(CorpEmployee::getCorpId, corpId));
        if (res == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_NOT_EXISTED_ERROR, "corpId: " + corpId + "employeeId: " + employeeId);
        }
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getCompanyId, corpId).eq(User::getEmployeeId, employeeId));
        if (user != null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_NOT_EXISTED_ERROR, "existed companyId and employeeId");
        }
        return corpId;

    }

    private void checkUserInfo(RegisterDTO registerDTO) {
        // 1. check field
        isIllegal(registerDTO);
        // 2. check user existed
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, registerDTO.getEmail()), false);
        if (user != null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_NOT_EXISTED_ERROR, registerDTO.getEmail());
        }
    }


    private void isIllegal(RegisterDTO registerDTO) {
        Integer roleType = Integer.valueOf(registerDTO.getRole_type());
        if (roleType > 2 && roleType < 0) {
            throw GeneralExceptionFactory.create(ErrorCode.ILLEGAL_DATA, "illegal roleType");
        }
    }

    private User setNewUser(User user, RegisterDTO registerDTO) {
        user.setPassword(encryptPass(registerDTO.getPassword()));
        user.setFname(registerDTO.getFname());
        user.setLname(registerDTO.getLname());
        user.setEmail(registerDTO.getEmail());
        user.setRoleType(registerDTO.getRole_type());
        user.setPhoneNum(registerDTO.getPhoneNum());
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

