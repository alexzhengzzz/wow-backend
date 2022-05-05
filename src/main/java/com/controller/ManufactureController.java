package com.controller;

import com.bo.ManufactureBO;
import com.business.ManufactureBusiness;
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
@RequestMapping("/api/ManufactureMaintain")
@Api("Manufacture")
public class ManufactureController {
    @Autowired
    ManufactureBusiness manufactureBusiness;
    @ApiOperation("getManufactureInfo")
    @GetMapping("getManufactureInfo")
    public Response<List<ManufactureBO>> getAllManufactureInfo(){
        return new Response<>(ResponseCode.SUCCESS, manufactureBusiness.getManufactureInfo());
    }
}
