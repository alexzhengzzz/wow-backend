package com.controller;

import com.annotation.SystemLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/")
@Slf4j
@Api("controller only for test")
public class GoController {

    @ApiOperation("connection test")
    @GetMapping("/go")
    @SystemLog(businessName = "go interface")
    public String go() {
        log.info("connection test success");
        return "success";
    }

}