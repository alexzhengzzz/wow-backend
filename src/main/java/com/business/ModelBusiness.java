package com.business;

import com.bo.ModelSelectBO;
import com.bo.ModelBO;

import java.util.List;

public interface ModelBusiness {
    List<ModelBO> getModelInfo();

    List<ModelSelectBO> getModelList();
}
