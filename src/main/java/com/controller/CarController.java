package com.controller;

import com.business.CarBusiness;
import com.dto.LoginDTO;
import com.utils.cache.Response;
import com.vo.CarVO;
import com.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/car")
@Api("Car")
public class CarController{

    @Autowired
    CarBusiness carBusiness;

    @ApiOperation("get carInfo by ID")
    @GetMapping
    public Response<CarVO> get(){
        CarVO carVO = carBusiness.getInfo();
        return new Response<>(carVO);
    }
}
