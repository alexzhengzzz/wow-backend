package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Vehicle;
import com.enums.VehicleStatus;
import com.vo.VehicleVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
public interface VehicleService extends IService<Vehicle> {
    List<VehicleVO> getVehicleInfo();

    boolean updateVehicleStatus(String vehicleId, VehicleStatus status);
}
