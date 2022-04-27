package com.controller;

import com.service.IUserService;
import com.utils.cache.Response;
import com.business.LoginBusiness;
import com.dto.LoginDTO;
import com.dto.RegisterDTO;
import com.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api("login")
public class LoginController {

    @Autowired
    private IUserService userService;

    @Autowired
    private LoginBusiness loginBusiness;

    @PostMapping("/login")
    public Response<UserVO> login(@RequestBody LoginDTO loginDTO) {
        UserVO userVo = loginBusiness.login(loginDTO);
        return new Response<>(userVo);
    }

    @PostMapping("/register")
    public Response<UserVO> register(@RequestBody RegisterDTO registerDTO) {
        UserVO userVo = loginBusiness.register(registerDTO);
        return new Response<>(userVo);
    }


}