package com.controller;

import com.annotation.SystemLog;
import com.utils.cache.IGlobalCache;
import com.utils.cache.Response;
import com.utils.cache.impl.RedisCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 *
 * test controller
 */
@RestController()
@RequestMapping("/")
@Slf4j
public class GoController {
//    @Resource
//    private DefaultRedisScript<Boolean> redisScript;
//    @Resource
//    private StringRedisTemplate stringRedisTemplate;


    @GetMapping("/go")
    @SystemLog(businessName = "go interface")
    public String go() {
        log.info("connection test success");
        return "success";
    }

//    @GetMapping("/lua")
//    public Response<Boolean> lua() {
//        List<String> keys = Arrays.asList("testLua", "hello lua");
//        Boolean execute = stringRedisTemplate.execute(redisScript, keys, "100");
//        return new Response<>(execute);
//    }

}