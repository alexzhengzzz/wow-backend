package com.controller;

import com.business.CarInfoBusiness;
import com.dto.CarInfoDTO;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carInfo")
@Api("CarInfo")
public class CarInfoController {
    @Autowired
    private CarInfoBusiness carInfoBusiness;

    @ApiOperation(" get carInfo list 0:entire, 1:in-stock, 2:invalid")
    @GetMapping("/{opt}")
    public Response getCarList(@PathVariable("opt") int opt) {
        if(opt!=1 && opt!=0 && opt!=2){
            return new Response(ResponseCode.WRONG_OPTION, ResponseCode.WRONG_OPTION.getMsg() );
        }
        return new Response<>(carInfoBusiness.getCarList(opt));
    }

//    @ApiOperation("get in-stock carList")
//    @GetMapping("/valid")
//    public Response getValidCarList() {
//        return new Response<>(carInfoBusiness.getCarList(1));
//    }
//
//    @ApiOperation("get invalid carlist")
//    @GetMapping("/invalid")
//    public Response getInvalidCarList() {
//        return new Response<>(carInfoBusiness.getCarList(2));
//    }

    @ApiOperation("create carInfo")
    @PostMapping("")
    public Response createCar(@RequestBody CarInfoDTO carInfoDTO){
        carInfoBusiness.updateCar(carInfoDTO,false);
        return new Response(ResponseCode.SUCCESS, "carInfo created");
    }

    @ApiOperation("update carInfo")
    @PutMapping()
    public Response updateCar(@RequestBody CarInfoDTO carInfoDTO){
        carInfoBusiness.updateCar(carInfoDTO, true);
        return new Response(ResponseCode.SUCCESS, "carInfo updated");
    }

    @ApiOperation("delete vehicle")
    @DeleteMapping()
    public Response deleteCar(String vinId){
        carInfoBusiness.deleteCar(vinId);
        return new Response(ResponseCode.SUCCESS, "carInfo deleted");
    }


}
