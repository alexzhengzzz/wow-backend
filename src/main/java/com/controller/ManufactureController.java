package com.controller;

import com.bo.ManufactureBO;
import com.business.ManufactureBusiness;
import com.dto.ManufactureDTO;
import com.enums.ResponseCode;
import com.service.IManufactureService;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("delete manufacuture information")
    @DeleteMapping("deleteManufacture/{manId}")
    public Response deleteManufacture(@PathVariable("manId") Integer manId){
        manufactureBusiness.deleteManufacture(manId);
        return new Response(ResponseCode.SUCCESS, "success");
    }

    @ApiOperation("update manufacutre information")
    @PutMapping("updateManufacture/{manId}")
    public Response upadteManufacture(@PathVariable("manId") Integer manId, ManufactureDTO manufactureDTO){
        manufactureBusiness.updateManufacture(manId, manufactureDTO);
        return new Response(ResponseCode.SUCCESS, "success");
    }

    @ApiOperation("create new manufacture")
    @PostMapping("createManufacture")
    public Response createManufacture(@RequestBody ManufactureDTO manufactureDTO){
        manufactureBusiness.createManufacture(manufactureDTO);
        return new Response(ResponseCode.SUCCESS, "success");
    }
}
