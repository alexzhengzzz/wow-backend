package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dto.VehicleInfoDTO;
import com.entity.info.CarInfo;
import com.mapper.CarInfoMapper;
import com.service.ICarInfoService;
import com.service.util.CarInfoUtil;
import com.vo.CarInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class CarInfoServiceImpl extends ServiceImpl<CarInfoMapper, CarInfo> implements ICarInfoService {
    @Autowired
    private CarInfoMapper carInfoMapper;

    @Override
    public List<CarInfoVO> getCarList() {
        return CarInfoUtil.transferList(carInfoMapper.getCarList());
    }

}
