package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bean.CouponCacheBuilder;
import com.entity.CouponsBatch;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CachePrepareServiceImpl implements InitializingBean {
    @Autowired
    private CouponCacheBuilder couponCacheBuilder;

    @Autowired
    private CouponsBatchServiceImpl couponsBatchService;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<CouponsBatch> couponsBatchList = couponsBatchService.list(new LambdaQueryWrapper<CouponsBatch>().ge(CouponsBatch::getStock,0));
        for (CouponsBatch couponsBatch : couponsBatchList) {
            couponCacheBuilder.prepare(couponsBatch.getBatchId(), couponsBatch.getStock());
        }
    }
}
