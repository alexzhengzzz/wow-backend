package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Model;
import com.vo.ModelVO;

import java.util.List;

public interface IModelService extends IService<Model> {
    List<ModelVO>  getModelInfo();
}
