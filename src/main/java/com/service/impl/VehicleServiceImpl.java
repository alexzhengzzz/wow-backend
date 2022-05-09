package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Vehicle;
import com.enums.VehicleStatus;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.VehiclesMapper;
import com.service.IVehicleService;
import com.service.util.VehicleUtil;
import com.vo.VehicleVO;
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
public class VehicleServiceImpl extends ServiceImpl<VehiclesMapper, Vehicle> implements IVehicleService {
   @Override
    public Vehicle getVehicleById(String id) {
        Vehicle vehicle = baseMapper.selectById(id);
        if (vehicle == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "vehicle  id not found");
        }
        return vehicle;
    }

    @Override
    public List<VehicleVO> getVehicleInfo() {
        QueryWrapper<Vehicle> queryWrapper = new QueryWrapper<>();
        queryWrapper.select();
        List<Vehicle> vehicleList = this.baseMapper.selectList(queryWrapper);
        if(vehicleList == null){
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "Vehicle Table is Empty");
        }
        return VehicleUtil.transferList(vehicleList);
    }

    @Override
    public boolean updateVehicleStatus(String vehicleId, VehicleStatus status) {
        Vehicle vehicle = getVehicleById(vehicleId);
        vehicle.setStatus(status.getStatus());
        this.baseMapper.updateById(vehicle);
        return true;
    }
}
