package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Coupons;
import com.entity.User;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.CouponsMapper;
import com.service.ICouponsService;
import org.springframework.stereotype.Service;

@Service
public class CouponsServiceImpl extends ServiceImpl<CouponsMapper, Coupons> implements ICouponsService {
    public Coupons getUserById(Long id) {
        Coupons coupons = this.baseMapper.selectById(id);
        if (coupons != null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "coupons id not found");
        }
        return coupons;
    }
}
