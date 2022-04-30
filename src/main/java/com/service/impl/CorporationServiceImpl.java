package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Corporation;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.CorporationMapper;
import com.service.ICorporationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zmh
 * @since 2022-04-26
 */
@Service
public class CorporationServiceImpl extends ServiceImpl<CorporationMapper, Corporation> implements ICorporationService {

    @Autowired
    private CorporationMapper corporationMapper;

    public Corporation getCorporationByName(String corporationName) {

        Corporation corporation = corporationMapper.selectOne(new LambdaQueryWrapper<Corporation>().eq(Corporation::getCompanyName, corporationName));
        if (corporation == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "no such corporation");
        }
        return corporation;
    }
}
