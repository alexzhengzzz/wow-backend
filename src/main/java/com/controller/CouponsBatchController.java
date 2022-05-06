package com.controller;

import com.business.CouponsBatchBussiness;
import com.dto.CouponsBatchDTO;
import com.entity.CouponsBatch;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zmh
 * @since 2022-04-30
 */
@RestController
@RequestMapping("/api/couponsbatch")
public class CouponsBatchController {
    @Autowired
    private CouponsBatchBussiness couponsBatchBussiness;

    @PostMapping
    public Response<CouponsBatch> couponsBatch(@Valid @RequestBody CouponsBatchDTO couponsBatchDTO){
        CouponsBatch couponsBatch = couponsBatchBussiness.createCouponsBatch(couponsBatchDTO);
        return new Response<>(ResponseCode.SUCCESS, couponsBatch);
    }

    @ApiOperation("get all the coupons batch, admin only!!")
    @GetMapping
    public Response<List<CouponsBatch>> getCouponsBatchList(){
        List<CouponsBatch> couponsBatchList = couponsBatchBussiness.getCouponsBatchList();
        return new Response<>(ResponseCode.SUCCESS, couponsBatchList);
    }

    @ApiOperation("get page by page and size, admin only!!")
    @GetMapping("/{page}/{size}")
    public Response<List<CouponsBatch>> getCouponsBatchListByPage(@PathVariable("page") Long page, @PathVariable("size") Long size){
        List<CouponsBatch> couponsBatchList = couponsBatchBussiness.getCouponsBatchListByPage(page,size);
        return new Response<>(ResponseCode.SUCCESS, couponsBatchList);
    }
}
