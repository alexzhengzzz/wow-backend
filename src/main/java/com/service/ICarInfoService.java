package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dto.VehicleInfoDTO;
import com.vo.CarInfoVO;

import java.util.List;

public interface ICarInfoService extends IService<VehicleInfoDTO> {
    List<CarInfoVO> getCarList();
}
