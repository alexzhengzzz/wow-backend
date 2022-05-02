package com.controller;

import com.business.CarInfoBusiness;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carInfo")
@Api("CarInfo")
public class CarInfoController {
    @Autowired
    private CarInfoBusiness carInfoBusiness;

    @ApiOperation("get entire carList")
    @GetMapping("entire")
    public Response getAllCarList(){
        return new Response<>(carInfoBusiness.getCarList(0));
    }

    @ApiOperation("get in-stock carList")
    @GetMapping("valid")
    public Response getValidCarList(){
        return new Response<>(carInfoBusiness.getCarList(1));
    }

    @ApiOperation("get invalid carlist")
    @GetMapping("invalid")
    public Response getInvalidCarList(){
        return new Response<>(carInfoBusiness.getCarList(2));
    }
}
