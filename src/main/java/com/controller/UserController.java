package com.controller;

import com.bean.Response;
import com.dto.User;
import com.service.UserService;
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
        return new Response<User>(user);
    }





}