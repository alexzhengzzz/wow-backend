package com.business.util;

import com.bo.ModelSelectBO;
import com.bo.ModelBO;
import com.vo.ModelVO;

import java.util.ArrayList;
import java.util.List;

public class ModelBOUtil {

    public static ModelBO transferToBO(ModelVO modelVO){
        ModelBO modelBO = new ModelBO();
        modelBO.setModelId(modelVO.getModelId());
        modelBO.setModelName(modelVO.getModelName());
        modelBO.setManId(modelVO.getModelId());
        modelBO.setSeatNum(modelVO.getSeatNum());
        modelBO.setYear(modelVO.getYear());
        return modelBO;
    }

    public static List<ModelBO> transferToBOList(List<ModelVO> modelVOList){
        List<ModelBO> modelBOList = new ArrayList<>();
        for(ModelVO modelVO:modelVOList){
            modelBOList.add(transferToBO(modelVO));
        }
        return modelBOList;
    }

    public static ModelSelectBO transferToBOPair(ModelVO modelVO){
        ModelSelectBO modelSelectBO = new ModelSelectBO();
        modelSelectBO.setModelId(modelVO.getModelId());
        modelSelectBO.setModelName(modelVO.getModelName());
        return modelSelectBO;
    }

    public static List<ModelSelectBO> transferToBOPairList(List<ModelVO> modelVOList){
        List<ModelSelectBO> modelSelectBOList = new ArrayList<>();
        for(ModelVO modelVO:modelVOList){
            modelSelectBOList.add(transferToBOPair(modelVO));
        }
        return modelSelectBOList;
    }
}
