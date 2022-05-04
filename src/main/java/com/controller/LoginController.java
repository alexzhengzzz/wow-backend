package com.controller;

import com.business.LoginBusiness;
import com.dto.LoginDTO;
import com.dto.RegisterDTO;
import com.enums.ResponseCode;
import com.service.UserService;
import com.utils.cache.Response;
import com.vo.TokenInfoVO;
import com.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api("login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginBusiness loginBusiness;

    @PostMapping("/login")
    public Response<UserVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        UserVO userVo = loginBusiness.login(loginDTO);
        return new Response<>(ResponseCode.SUCCESS, userVo);
    }

    @PostMapping("/register")
    public Response<UserVO> register(@Valid @RequestBody RegisterDTO registerDTO) {
        UserVO userVo = loginBusiness.register(registerDTO);
        return new Response<>(ResponseCode.SUCCESS, userVo);
    }

    @ApiOperation("decode token, [add request params], e.g: token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUz")
    @PostMapping("/admin/token")
    public Response<TokenInfoVO> refreshToken(@RequestParam("token") String token) {
        TokenInfoVO newToken = loginBusiness.refreshToken(token);
        return new Response<>(ResponseCode.SUCCESS, newToken);
    }
}