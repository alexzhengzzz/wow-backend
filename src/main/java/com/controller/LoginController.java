package com.controller;

import com.enums.ResponseCode;
import com.service.UserService;
import com.utils.cache.Response;
import com.business.LoginBusiness;
import com.dto.LoginDTO;
import com.dto.RegisterDTO;
import com.vo.UserVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("login")
public class LoginController {

    @Autowired
    private UserService userService;

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

    @PostMapping("/token")
    public Response<String> refreshToken(@RequestParam("token") String token) {
        String newToken = loginBusiness.refreshToken(token);
        return new Response<>(ResponseCode.SUCCESS, newToken);
    }


}