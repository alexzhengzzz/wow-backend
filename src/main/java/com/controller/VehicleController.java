package com.controller;

import com.bo.VehicleSelectBO;
import com.bo.VehicleBO;
import com.business.VehicleBusiness;
import com.dto.PaymentDTO;
import com.dto.VehicleDTO;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
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
    @GetMapping("/getVehiclePlateList")
    public Response<List<VehicleSelectBO>> getAllVehicleList(){
        return new Response<>(ResponseCode.SUCCESS, vehicleBusiness.getVehicleList());
    }

    @ApiOperation("get availabe return office information")
    @GetMapping("/getVehicleInfo")
    public Response<List<VehicleBO>> getAllVehicleInfo(){
        return new Response<>(ResponseCode.SUCCESS, vehicleBusiness.getVehicleInfo());
    }

    @ApiOperation("delete vehicle by vinId")
    @DeleteMapping("/deleteVehicle/{vinId}")
    public Response deleteVehicle(@PathVariable("vinId")  String vinId ){
        vehicleBusiness.deleteVehicleById(vinId);
        return new Response(ResponseCode.SUCCESS,"vehicle deleted");
    }

    @ApiOperation("update vehicle by vinId")
    @PutMapping("/updateVehicle/{vinId}")
    public Response updateVehicle(@PathVariable("vinId") String vinId,@RequestBody VehicleDTO vehicleDTO){
        vehicleBusiness.updateVehicle(vinId, vehicleDTO);
        return new Response(ResponseCode.SUCCESS,"vehicle updated");
    }

    @ApiOperation("create vehicle")
    @PostMapping("/createVehicle")
    public Response createVehicle(@RequestBody VehicleDTO vehicleDTO){
        vehicleBusiness.createVehicleInfo(vehicleDTO);
        return new Response(ResponseCode.SUCCESS,"vehicle created");
    }

//    @ApiOperation("set vehicle status")
//    @GetMapping("setVehicleStatus")
//    public Response setVehicleStatus(){
//        ;
//    }

}
