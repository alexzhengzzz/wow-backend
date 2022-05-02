package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.CarClass;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.CarClassMapper;
import com.service.CarClassService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
@Service
public class CarClassServiceImpl extends ServiceImpl<CarClassMapper, CarClass> implements CarClassService {
    CarClass getCarClassInfoById(Integer id) {
        CarClass carClass = baseMapper.selectById(id);
        if (carClass == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "car class id not found");
        }
        return carClass;
    }
}
