package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bean.Response;
import com.dto.LoginDTO;
import com.dto.RegisterDTO;
import com.entity.User;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.UserService;
import com.utils.cache.JWTUtils;
import com.vo.UserVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("login")
public class LoginController {

    @Autowired
    private UserService userService;

//    @GetMapping("/{userId}")
//    public Response<User> getUserById(@PathVariable(value = "userId", required = true) Integer id) {
//        User user = userService.getById(1);
//        return new Response<>(user);
//    }

    @PostMapping("/login")
    public Response<UserVO> login(@RequestBody LoginDTO loginDTO) {
        // 1. 查找该用户
        User user = userService.getById(loginDTO.getId());
        // 2. 用户不存在
        if (user == null) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_NOT_FOUND, loginDTO.getId());
        }
        // 3. 密码校验
        String loginMD5pass = encryptPass(loginDTO.getPassword());
        if (!user.getPassword().equals(loginMD5pass)) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_PASSWORD_WRONG, loginDTO.getId());
        }
        // 4. UserVO
        String token = JWTUtils.createToken(user.getId().toString());
        UserVO userVo = getUserVO(user, token);
        return new Response<>(userVo);
    }

    @PostMapping("/register")
    public Response<UserVO> register(@RequestBody RegisterDTO registerDTO) {
            // 1. 查找该用户 根据id, name
        User user = userService.getOne(getQueryWrapperUser(registerDTO), false);
        // 2. 用户已经存在
        if (user != null) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_INFO_EXISTED, registerDTO.getId(), registerDTO.getName());
        }
        // 3. 新建用户加入数据库
        User newUser = setNewUser(registerDTO);
        boolean isSuccess = userService.save(newUser);
        // 4. 插入失败
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.INSERT_USER_ERROR, registerDTO.getId());
        }
        // 4. 插入成功
        String token = JWTUtils.createToken(newUser.getId().toString());
        UserVO userVo = getUserVO(newUser, token);
        return new Response<>(userVo);

    }

    private User setNewUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setId(registerDTO.getId());
        user.setPassword(encryptPass(registerDTO.getPassword()));
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setRole_type(registerDTO.getRole_type());
        return user;
    }

    private QueryWrapper<User> getQueryWrapperUser(RegisterDTO registerDTO) {
        QueryWrapper<User> q = new QueryWrapper<>();
        q.eq("id", registerDTO.getId()).or().eq("name", registerDTO.getName());
        return q;
    }

    private UserVO getUserVO(User user, String token) {
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setName(user.getName());
        userVO.setToken(token);
        return userVO;
    }

    private String encryptPass(String pass) {
        return DigestUtils.md5DigestAsHex(pass.getBytes());
    }


}