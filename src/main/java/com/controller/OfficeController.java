package com.controller;

import com.bo.AvailableOfficeBO;
import com.bo.OfficeBO;
import com.business.OfficeMaintainBusiness;
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
@RequestMapping("/api/officeMaintain")
@Api("OfficeMaintain")
public class OfficeController {
    @Autowired
    OfficeMaintainBusiness officeMaintainBusiness;

    @ApiOperation("get availabe return office")
    @GetMapping("OfficeNameList")
    public Response<List<AvailableOfficeBO>> getAllOfficeList(){
        return new Response<>(ResponseCode.SUCCESS, officeMaintainBusiness.getOfficeList());
    }

    @ApiOperation("get availabe return office information")
    @GetMapping("OfficeInformation")
    public Response<List<OfficeBO>> getAllOfficeInfo(){
        return new Response<>(ResponseCode.SUCCESS, officeMaintainBusiness.getOfficeInfo());
    }

}
