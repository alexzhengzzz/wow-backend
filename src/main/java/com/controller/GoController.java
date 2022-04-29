package com.controller;

import com.annotation.PermissionChecker;
import com.annotation.SystemLog;
import com.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GoController {
    private final Logger logger = LoggerFactory.getLogger(GoController.class);


    @GetMapping("/go")
    @SystemLog(businessName = "go interface")
    public String go() {
        logger.info("logger test success");
        return "success";
    }
}