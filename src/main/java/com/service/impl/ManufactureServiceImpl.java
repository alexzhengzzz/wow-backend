package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Manufacture;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.ManufactureMapper;
import com.service.IManufactureService;
import com.service.util.ManufactureUtil;
import com.vo.ManufactureVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufactureServiceImpl extends ServiceImpl<ManufactureMapper, Manufacture> implements IManufactureService {

    @Override
    public List<ManufactureVO> getManufactureInfo() {
        QueryWrapper<Manufacture> queryWrapper = new QueryWrapper<>();
        queryWrapper.select();
        List<Manufacture> manufactureList   = this.baseMapper.selectList(queryWrapper);
        if(manufactureList == null){
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "Manufacture Table is Empty");
        }
        return ManufactureUtil.transferList(manufactureList);
    }

    @Override
    public int insert(Manufacture manufacture) {
        return this.baseMapper.insert(manufacture);
    }
}
