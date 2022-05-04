package com.controller;

import com.bo.AvailableOfficeBO;
import com.bo.CarInfoBO;
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
public class OfficeMaintainController {
    @Autowired
    OfficeMaintainBusiness officeMaintainBusiness;

    @ApiOperation("get availabe return office")
    @GetMapping("return office")
    public Response<List<AvailableOfficeBO>> getAllCarList(){
        return new Response<>(ResponseCode.SUCCESS, officeMaintainBusiness.getOfficeList());
    }
}
