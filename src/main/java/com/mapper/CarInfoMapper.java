package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.info.CarInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//@Mapper
//@Repository
public interface CarInfoMapper extends BaseMapper<CarInfo> {
    @Autowired
    List<CarInfo> getCarList();
}
