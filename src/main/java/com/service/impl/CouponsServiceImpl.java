package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Coupons;
import com.mapper.CouponsMapper;
import com.service.ICouponsService;
import org.springframework.stereotype.Service;

@Service
public class CouponsServiceImpl extends ServiceImpl<CouponsMapper, Coupons> implements ICouponsService {
}
