package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.VehicleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//@Mapper
//@Repository
public interface CarInfoMapper extends BaseMapper<VehicleInfo> {
    @Autowired
    public VehicleInfo getCarList();
}
