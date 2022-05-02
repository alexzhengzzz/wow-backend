package com.service.impl;

import com.entity.CarClass;
import com.entity.Vehicle;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.VehiclesMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.service.VehicleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
@Service
public class VehicleServiceImpl extends ServiceImpl<VehiclesMapper, Vehicle> implements VehicleService {
    public Vehicle getVehicleById(String id) {
        Vehicle vehicle = baseMapper.selectById(id);
        if (vehicle == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "vehicle  id not found");
        }
        return vehicle;
    }
}
