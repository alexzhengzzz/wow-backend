package com.controller;

import com.annotation.SystemLog;
import com.utils.cache.IGlobalCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/test")
@Slf4j
@Api("controller only for test")
public class GoController {
    @Autowired
    private IGlobalCache globalCache;
    @ApiOperation("connection test")
    @GetMapping
    @SystemLog(businessName = "go interface")
    public String go() {
        log.info("connection test success");
        return "success";
    }

    @ApiOperation("cache test")
    @GetMapping("/cache/{key}")
    public String cache(@PathVariable Integer key) {
        globalCache.get("test:" + key);
        return (String) globalCache.get("test:" + key);
    }

}