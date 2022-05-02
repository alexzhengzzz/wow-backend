package com.controller;

import com.business.VehicleInfoBusiness;
import com.dto.CorporationDTO;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import com.vo.VehicleInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carinfo")
@Api("CarInfo")
public class CarInfoController {
    @Autowired
    private VehicleInfoBusiness vehicleInfoBusiness;

    @ApiOperation("get entire carlist")
    @GetMapping
    public Response<List<VehicleInfoVO>> getAllCarList(){
        return new Response<>(ResponseCode.SUCCESS, vehicleInfoBusiness.getCarList());
    }
}
