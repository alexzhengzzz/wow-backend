package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dto.VehicleInfoDTO;

public interface ICarInfoService extends IService<VehicleInfoDTO> {
    VehicleInfoDTO getCarList();
}
