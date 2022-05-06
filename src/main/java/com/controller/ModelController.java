package com.controller;

import com.bo.ModelSelectBO;
import com.bo.ModelBO;
import com.business.ModelBusiness;
import com.dto.ManufactureDTO;
import com.dto.ModelDTO;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ModelMaintain")
@Api("Model")
public class ModelController {
    @Autowired
    ModelBusiness modelBusiness;

    @ApiOperation("get availabe return class list")
    @GetMapping("/getModelNameList")
    public Response<List<ModelSelectBO>> getAllModelList(){
        return new Response<>(ResponseCode.SUCCESS, modelBusiness.getModelList());
    }

    @ApiOperation("get availabe return class information")
    @GetMapping("/getModelInfo")
    public Response<List<ModelBO>> getAllModelOInfo(){
        return new Response<>(ResponseCode.SUCCESS, modelBusiness.getModelInfo());
    }

    @ApiOperation("delete manufacuture information")
    @DeleteMapping("/deleteModel/{modelId}")
    public Response deleteModel(@PathVariable("modelId") Integer modelId){
        modelBusiness.deleteModel(modelId);
        return new Response(ResponseCode.SUCCESS, "success");
    }

    @ApiOperation("update manufacutre information")
    @PutMapping("/updateModel/{modelId}")
    public Response upadteModel(@PathVariable("modelId") Integer modelId, ModelDTO modelDTO){
        modelBusiness.updateModel(modelId, modelDTO);
        return new Response(ResponseCode.SUCCESS, "success");
    }

    @ApiOperation("create new manufacture")
    @PostMapping("/createManufacture")
    public Response createModel(@RequestBody ModelDTO modelDTO){
        modelBusiness.createModel(modelDTO);
        return new Response(ResponseCode.SUCCESS, "success");
    }
}
