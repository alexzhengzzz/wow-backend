package com.bean;

import com.utils.cache.IGlobalCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
@Slf4j
public class CouponCacheBuilder{

    @Autowired
    private IGlobalCache globalCache;

    private static final String COUPON_CACHE = "coupon:stock:";
    private String getCacheKey(Long id) {
        return COUPON_CACHE.concat(String.valueOf(id));
    }
    public void prepare(Long id, int totalCount) {
        String key = getCacheKey(id);
        Map<String, Object> goods = new HashMap<>(1);
        goods.put("totalCount", totalCount);
        globalCache.hmset(key, goods);
    }
}
