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
@RequestMapping("/api/vehicle")
@Api("VehicleMaintain")
public class VehicleController {

    @Autowired
    VehicleBusiness vehicleBusiness;

    @ApiOperation("get available return vehicle")
    @GetMapping("/plateList")
    public Response<List<VehicleSelectBO>> getAllVehicleList(){
        return new Response<>(ResponseCode.SUCCESS, vehicleBusiness.getVehicleList());
    }

    @ApiOperation("get available vehicle information")
    @GetMapping()
    public Response<List<VehicleBO>> getAllVehicleInfo(){
        return new Response<>(ResponseCode.SUCCESS, vehicleBusiness.getVehicleInfo());
    }

    @ApiOperation("delete vehicle by vinId")
    @DeleteMapping("/{vinId}")
    public Response deleteVehicle(@PathVariable("vinId")  String vinId ){
        vehicleBusiness.deleteVehicleById(vinId);
        return new Response(ResponseCode.SUCCESS,"vehicle deleted");
    }

    @ApiOperation("update vehicle by vinId")
    @PutMapping("/{vinId}")
    public Response updateVehicle(@PathVariable("vinId") String vinId,@RequestBody VehicleDTO vehicleDTO){
        vehicleBusiness.updateVehicle(vinId, vehicleDTO);
        return new Response(ResponseCode.SUCCESS,"vehicle updated");
    }

    @ApiOperation("create vehicle")
    @PostMapping()
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
