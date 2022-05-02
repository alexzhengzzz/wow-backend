package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.CarInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//@Mapper
//@Repository
public interface CarInfoMapper extends BaseMapper<CarInfo> {
    @Autowired
    public List<CarInfo> getCarList();
}
