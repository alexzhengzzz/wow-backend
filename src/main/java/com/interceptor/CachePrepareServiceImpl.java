package com.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bean.CouponCacheBuilder;
import com.entity.CouponsBatch;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.service.impl.CouponsBatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CachePrepareServiceImpl implements InitializingBean {
    @Autowired
    private CouponCacheBuilder couponCacheBuilder;

    @Autowired
    private CouponsBatchServiceImpl couponsBatchService;

    private BloomFilter<Long> couponsBatchBloomFilter = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<CouponsBatch> couponsBatchList = addCouponBatchToCache();
        couponsBatchBloomFilter = buildBloomFilter(couponsBatchList);
    }

    public BloomFilter<Long> getCouponsBatchBloomFilter() {

        return couponsBatchBloomFilter;
    }

    private List<CouponsBatch> addCouponBatchToCache() {
        List<CouponsBatch> couponsBatchList = couponsBatchService.list(new LambdaQueryWrapper<CouponsBatch>().ge(CouponsBatch::getStock, 0));
        for (CouponsBatch couponsBatch : couponsBatchList) {
            couponCacheBuilder.prepare(couponsBatch.getBatchId(), couponsBatch.getStock());
        }
        return couponsBatchList;
    }

    private BloomFilter<Long> buildBloomFilter(List<CouponsBatch> couponsBatchList) {
        // hash function
        BloomFilter<Long> couponsBatchBloomFilter = BloomFilter.create(Funnels.longFunnel(), 500, 0.01);
        // add data
        for (CouponsBatch couponsBatch : couponsBatchList) {
            couponsBatchBloomFilter.put(couponsBatch.getBatchId());
        }
        return couponsBatchBloomFilter;
    }

}
