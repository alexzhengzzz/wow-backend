package com.service.util;

import com.dto.ModelDTO;
import com.entity.Model;
import com.vo.ModelVO;

import java.util.ArrayList;
import java.util.List;

public class ModelUtil {
    public static ModelVO transfer(Model model){
        ModelVO modelVO = new ModelVO();
        modelVO.setModelId(model.getModelId());
        modelVO.setModelName(model.getModelName());
        modelVO.setManId(model.getManId());
        modelVO.setSeatNum(model.getSeatNum());
        modelVO.setYear(model.getYear());
        return modelVO;
    }

    public static List<ModelVO>   transferList(List<Model> modelList){
        List<ModelVO>  modelVOList = new ArrayList<>();
        for(Model model:modelList){
            modelVOList.add(transfer(model));
        }
        return modelVOList;
    }

    public static Model transferDTO(Integer modelId, ModelDTO modelDTO){
        Model model = new Model();
        model.setModelId(modelId);
        model.setModelName(modelDTO.getModelName());
        model.setManId(modelDTO.getManId());
        model.setSeatNum(modelDTO.getSeatNum());
        model.setYear(modelDTO.getYear());
        return model;
    }
}
