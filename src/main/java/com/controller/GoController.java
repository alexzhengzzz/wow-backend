package com.controller;

import com.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/")
@Slf4j
public class GoController {

    @GetMapping("/go")
    @SystemLog(businessName = "go interface")
    public String go() {
        log.info("connection test success");
        return "success";
    }
}