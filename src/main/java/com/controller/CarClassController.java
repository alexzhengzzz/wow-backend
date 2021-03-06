package com.controller;

import com.bo.CarClassSelectBO;
import com.bo.CarClassBO;
import com.business.CarClassBusiness;
import com.dto.CarClassDTO;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carClass")
@Api("ClassMaintain")
public class CarClassController {

    @Autowired
    CarClassBusiness carClassBusiness;

    @ApiOperation("get availabe return class list")
    @GetMapping("/nameList")
    public Response<List<CarClassSelectBO>> getAllClassList(){
        return new Response<>(ResponseCode.SUCCESS, carClassBusiness.getClassList());
    }

    @ApiOperation("get availabe return class information")
    @GetMapping()
    public Response<List<CarClassBO>> getAllClassOInfo(){
        return new Response<>(ResponseCode.SUCCESS, carClassBusiness.getClassInfo());
    }

    @ApiOperation("delete carClass by classId")
    @DeleteMapping("/{carClassId}")
    public Response deleteCarClassById(@PathVariable("carClassId") Integer carClassId){
        carClassBusiness.deleteCarClassById(carClassId);
        return new Response(ResponseCode.SUCCESS, "carClass created");
    }

    @ApiOperation("create carClass")
    @PostMapping("")
    public Response createCarClassById(@RequestBody CarClassDTO carClassDTO){
        carClassBusiness.createCarClassById(carClassDTO);
        return new Response(ResponseCode.SUCCESS, "carClass created");
    }

    @ApiOperation("update carClass by classId")
    @PutMapping("/{carClassId}")
    public Response updateCarClassById(@PathVariable("carClassId") Integer carClassId, @RequestBody CarClassDTO carClassDTO){
        carClassBusiness.updateCarClassById(carClassId,carClassDTO);
        return new Response(ResponseCode.SUCCESS, "carClass updated");
    }
}
