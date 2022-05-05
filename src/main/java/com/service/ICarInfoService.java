package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.info.CarInfo;
import com.vo.CarInfoVO;

import java.util.List;

public interface ICarInfoService extends IService<CarInfo> {
    List<CarInfoVO> getCarList();
}
