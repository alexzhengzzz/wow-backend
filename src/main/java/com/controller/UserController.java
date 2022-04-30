package com.controller;

import com.annotation.PermissionChecker;
import com.business.IUserBusiness;
import com.enums.ResponseCode;
import com.enums.Role;
import com.utils.cache.Response;
import com.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api("UserController")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserBusiness userBusiness;

    @GetMapping
    @ApiOperation("get user info, with valid token")
    public Response<UserInfoVO> getUserInfo() {
        UserInfoVO userInfoVO = userBusiness.getUserInfo();
        return new Response<>(userInfoVO);
    }
}
