package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.CarClass;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.CarClassMapper;
import com.service.ICarClassService;
import com.service.util.CarClassUtil;
import com.vo.CarClassVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
@Service
public class CarClassServiceImpl extends ServiceImpl<CarClassMapper, CarClass> implements ICarClassService {
    @Override
    public CarClass getCarClassInfoById(Integer id) {
        CarClass carClass = baseMapper.selectById(id);
        if (carClass == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "car class id not found");
        }
        return carClass;
    }

    @Override
    public List<CarClassVO> getCarClassInfo() {
        QueryWrapper<CarClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.select();
        List<CarClass> carClasseList = this.baseMapper.selectList(queryWrapper);
        if(carClasseList   == null){
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "CarClass Table is Empty");
        }
        return CarClassUtil.transferList(carClasseList);
    }

    @Override
    public Integer insert(CarClass carClass) {
        return this.baseMapper.insert(carClass);
    }
}
