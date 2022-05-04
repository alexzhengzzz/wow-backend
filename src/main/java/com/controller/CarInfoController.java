package com.controller;

import com.bo.CarInfoBO;
import com.business.CarInfoBusiness;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Response<List<CarInfoBO>> getAllCarList(){
        return new Response<>(ResponseCode.SUCCESS,carInfoBusiness.getCarList(0));
    }

    @ApiOperation("get in-stock carList")
    @GetMapping("valid")
    public Response<List<CarInfoBO>> getValidCarList(){
        return new Response<>(ResponseCode.SUCCESS, carInfoBusiness.getCarList(1));
    }

    @ApiOperation("get invalid carlist")
    @GetMapping("invalid")
    public Response<List<CarInfoBO>> getInvalidCarList(){
        return new Response<>(ResponseCode.SUCCESS, carInfoBusiness.getCarList(2));
    }
}
