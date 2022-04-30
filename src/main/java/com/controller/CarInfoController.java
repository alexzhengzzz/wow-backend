package com.controller;

import com.business.VehicleInfoBusiness;
import com.dto.CorporationDTO;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carinfo")
@Api("CarInfo")
public class CarInfoController {
    @Autowired
    private VehicleInfoBusiness vehicleInfoBusiness;

    @ApiOperation("get entire carlist")
    @GetMapping
    public Response getAllCarList(){
        return new Response<>(vehicleInfoBusiness.getCarList());
    }
}
