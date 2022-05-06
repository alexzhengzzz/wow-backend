package com.controller;

import com.bo.VehicleSelectBO;
import com.bo.VehicleBO;
import com.business.VehicleBusiness;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carMaintain")
@Api("VehicleMaintain")
public class VehicleController {

    @Autowired
    VehicleBusiness vehicleBusiness;

    @ApiOperation("get availabe return vehicle")
    @GetMapping("getVehiclePlateList")
    public Response<List<VehicleSelectBO>> getAllVehicleList(){
        return new Response<>(ResponseCode.SUCCESS, vehicleBusiness.getVehicleList());
    }

    @ApiOperation("get availabe return office information")
    @GetMapping("getVehicleInformation")
    public Response<List<VehicleBO>> getAllVehicleInfo(){
        return new Response<>(ResponseCode.SUCCESS, vehicleBusiness.getVehicleInfo());
    }

//    @ApiOperation("set vehicle status")
//    @GetMapping("setVehicleStatus")
//    public Response setVehicleStatus(){
//        ;
//    }

}
