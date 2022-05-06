package com.business.impl;

import com.bo.ModelSelectBO;
import com.bo.ModelBO;
import com.business.ModelBusiness;
import com.business.util.ModelBOUtil;
import com.dto.ModelDTO;
import com.service.IModelService;
import com.service.util.ModelUtil;
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

    @Override
    public boolean deleteModel(Integer modelId) {
        return modelService.removeById(modelId);
    }

    @Override
    public boolean createModel(ModelDTO modelDTO) {
        return modelService.save(ModelUtil.transferDTO(null,modelDTO));
    }

    @Override
    public boolean updateModel(Integer modelId, ModelDTO modelDTO) {
        return modelService.updateById(ModelUtil.transferDTO(modelId,modelDTO));
    }
}
