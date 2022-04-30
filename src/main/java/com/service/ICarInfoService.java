package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dto.VehicleInfoDTO;

import java.util.List;

public interface ICarInfoService extends IService<VehicleInfoDTO> {
    List<VehicleInfoDTO> getCarList();
}
