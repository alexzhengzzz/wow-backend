package com.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.LoginBusiness;
import com.dto.LoginDTO;
import com.dto.RegisterDTO;
import com.entity.User;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.IUserService;
import com.utils.cache.JWTUtils;
import com.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class LoginBusinessImpl implements LoginBusiness {
    @Autowired
    private IUserService userService;

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
    public UserVO register(RegisterDTO registerDTO) {
        isIllegal(registerDTO);
        // 1. 判断是否存在
        User user = userService.getOne(getQueryWrapperUser(registerDTO), false);
        // 2. 用户已经存在
        if (user != null) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_INFO_EXISTED, registerDTO.getEmail(), registerDTO.getFname() + " " + registerDTO.getLname());
        }
        // 3. 新建用户加入数据库
        User newUser = setNewUser(registerDTO);
        boolean isSuccess = userService.save(newUser);
        // 4. 插入失败
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.INSERT_USER_ERROR, registerDTO.getEmail());
        }
        // 4. 插入成功
        String token = JWTUtils.createToken(newUser.getEmail());
        UserVO userVo = getUserVO(newUser, token);
        return userVo;
    }

    private void isIllegal(RegisterDTO registerDTO) {
        String email = registerDTO.getEmail();
        String fname = registerDTO.getFname();
        String lname = registerDTO.getLname();
        String password = registerDTO.getPassword();
        if (StringUtils.isBlank(email) || StringUtils.isBlank(fname) || StringUtils.isBlank(lname) || StringUtils.isBlank(password)) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_INFO_ILLEGAL);
        }
    }

    private User setNewUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setPassword(encryptPass(registerDTO.getPassword()));
        user.setFname(registerDTO.getFname());
        user.setLname(registerDTO.getLname());
        user.setEmail(registerDTO.getEmail());
        user.setRoleType(registerDTO.getRole_type());
        return user;
    }

    private QueryWrapper<User> getQueryWrapperUser(RegisterDTO registerDTO) {
        QueryWrapper<User> q = new QueryWrapper<>();
        q.eq("email", registerDTO.getEmail())
                .or(i -> i.eq("fname", registerDTO.getFname()).eq("lname", registerDTO.getLname()));
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

