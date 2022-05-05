package com.controller;

import com.bo.ModelSelectBO;
import com.bo.ModelBO;
import com.business.ModelBusiness;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ModelMaintain")
@Api("Model")
public class ModelController {
    @Autowired
    ModelBusiness modelBusiness;

    @ApiOperation("get availabe return class list")
    @GetMapping("getModelNameList")
    public Response<List<ModelSelectBO>> getAllModelList(){
        return new Response<>(ResponseCode.SUCCESS, modelBusiness.getModelList());
    }

    @ApiOperation("get availabe return class information")
    @GetMapping("getModelInfo")
    public Response<List<ModelBO>> getAllModelOInfo(){
        return new Response<>(ResponseCode.SUCCESS, modelBusiness.getModelInfo());
    }
}