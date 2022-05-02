package com.interceptor;

public interface RedisRateLimit {
    Boolean limit(String limit, Integer windowTime);
}
