package com.business.impl;

import com.bo.ModelSelectBO;
import com.bo.ModelBO;
import com.business.ModelBusiness;
import com.business.util.ModelBOUtil;
import com.service.IModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ModelBusinessImpl implements ModelBusiness {
    @Autowired
    IModelService modelService;
    @Override
    public List<ModelBO> getModelInfo() {
        return ModelBOUtil.transferToBOList(modelService.getModelInfo());
    }

    @Override
    public List<ModelSelectBO> getModelList() {
        return ModelBOUtil.transferToBOPairList(modelService.getModelInfo());
    }
}
