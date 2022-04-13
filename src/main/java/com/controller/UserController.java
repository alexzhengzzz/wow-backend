package com.controller;

import com.bean.Response;
import com.dto.LoginDTO;
import com.entity.User;
import com.service.UserService;
import com.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public Response<User> getUserById(@PathVariable(value = "userId", required = true) Integer id) {
        User user = userService.getById(1);
        return new Response<>(user);
    }

    @PostMapping("/login")
    public Response<UserVO> login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }





}