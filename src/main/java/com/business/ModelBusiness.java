package com.business;

import com.bo.ModelSelectBO;
import com.bo.ModelBO;
import com.dto.ModelDTO;

import java.util.List;

public interface ModelBusiness {
    List<ModelBO> getModelInfo();

    List<ModelSelectBO> getModelList();

    boolean deleteModel(Integer modelId);
    boolean createModel(ModelDTO modelDTO);

    boolean updateModel(Integer modelId, ModelDTO modelDTO);
}
