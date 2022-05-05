package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Model;
import com.entity.Office;
import com.entity.Vehicle;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.ModelMapper;
import com.mapper.OfficeMapper;
import com.service.IModelService;
import com.service.util.ModelUtil;
import com.service.util.VehicleUtil;
import com.vo.ModelVO;
import com.vo.VehicleVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model> implements IModelService{
    @Override
    public List<ModelVO> getModelInfo() {
        QueryWrapper<Model> queryWrapper = new QueryWrapper<>();
        queryWrapper.select();
        List<Model> modelList = this.baseMapper.selectList(queryWrapper);
        if(modelList == null){
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "Model Table is Empty");
        }
            return ModelUtil.transferList(modelList);
    }
}
