package com.interceptor.impl;

import com.interceptor.RedisRateLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RedisRateLimitImpl implements RedisRateLimit {
    private static final String FAIL_CODE = "0";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private DefaultRedisScript<String> redisScript;

    public Boolean limit(String limit, Integer window_time) {
        try {
            String key = String.valueOf(System.currentTimeMillis() / window_time);
            List<String> lis = new ArrayList<>();
            lis.add(key);
            // key current time
            String result = stringRedisTemplate.execute(redisScript, lis, limit);
            log.warn("result:{}", result);
            if (FAIL_CODE != result) {
                return true;
            }
        } catch (Exception e) {
            log.error(limit, e);
        } finally {
            log.warn("limit request");
        }
        return false;
    }
}
