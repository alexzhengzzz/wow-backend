package com.controller;

import com.bean.Response;
import com.business.LoginBusiness;
import com.dto.LoginDTO;
import com.dto.RegisterDTO;
import com.service.UserService;
import com.vo.UserVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginBusiness loginBusiness;
//    @GetMapping("/{userId}")
//    public Response<User> getUserById(@PathVariable(value = "userId", required = true) Integer id) {
//        User user = userService.getById(1);
//        return new Response<>(user);
//    }

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