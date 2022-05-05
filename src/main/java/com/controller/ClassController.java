package com.controller;

import com.bo.CarClassSelectBO;
import com.bo.CarClassBO;
import com.business.CarClassBusiness;
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
@RequestMapping("/api/ClassMaintain")
@Api("ClassMaintain")
public class ClassController {

    @Autowired
    CarClassBusiness carClassBusiness;

    @ApiOperation("get availabe return class list")
    @GetMapping("classNameList")
    public Response<List<CarClassSelectBO>> getAllClassList(){
        return new Response<>(ResponseCode.SUCCESS, carClassBusiness.getClassList());
    }

    @ApiOperation("get availabe return class information")
    @GetMapping("officeInformation")
    public Response<List<CarClassBO>> getAllClassOInfo(){
        return new Response<>(ResponseCode.SUCCESS, carClassBusiness.getClassInfo());
    }
}
