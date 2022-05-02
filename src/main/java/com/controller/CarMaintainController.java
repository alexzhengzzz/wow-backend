package com.controller;

import com.business.CarBusiness;
import com.utils.cache.Response;
import com.vo.CarVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carMaintain")
@Api("CarMaintain")
public class CarMaintainController {

    @Autowired
    CarBusiness carBusiness;

    @ApiOperation("未完成")
    @GetMapping
    public Response<CarVO> get(){
        CarVO carVO = carBusiness.getInfo();
        return new Response<>(carVO);
    }
}
