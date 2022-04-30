package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dto.VehicleInfoDTO;
import com.mapper.CarInfoMapper;
import com.service.ICarInfoService;
import com.utils.cache.CarInfoUtil;
import com.vo.VehicleInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class VehicleInfoServiceImpl implements ICarInfoService{
    @Autowired
    private CarInfoMapper carInfoMapper;

    @Override
    public List<VehicleInfoVO> getCarList(){
        return CarInfoUtil.transferList(carInfoMapper.getCarList());
    }

    @Override
    public boolean saveBatch(Collection<VehicleInfoDTO> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<VehicleInfoDTO> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<VehicleInfoDTO> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(VehicleInfoDTO entity) {
        return false;
    }

    @Override
    public VehicleInfoDTO getOne(Wrapper<VehicleInfoDTO> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<VehicleInfoDTO> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<VehicleInfoDTO> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<VehicleInfoDTO> getBaseMapper() {
        return null;
    }

    @Override
    public Class<VehicleInfoDTO> getEntityClass() {
        return null;
    }
}
