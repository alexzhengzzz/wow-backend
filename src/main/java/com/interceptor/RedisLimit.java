package com.interceptor;

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
public class RedisLimit {

    private static final String FAIL_CODE = "0";

    private static String limit = "20";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private DefaultRedisScript<String> redisScript;

    public Boolean limit() {
        try {
            String key = String.valueOf(System.currentTimeMillis() / 1000);
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
