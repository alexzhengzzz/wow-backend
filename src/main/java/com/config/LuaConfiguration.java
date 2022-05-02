package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
@Configuration
public class LuaConfiguration {
    // limit request
    @Bean
    public DefaultRedisScript<String> redisScript() {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/limit.lua")));
        redisScript.setResultType(String.class);
        return redisScript;
    }

    // stock = stock - 1
    @Bean
    public DefaultRedisScript<String> redisScript2() {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/decrcoupons.lua")));
        redisScript.setResultType(String.class);
        return redisScript;
    }


}
