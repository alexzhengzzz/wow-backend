package com.controller;

import com.business.CouponsBatchBussiness;
import com.dto.CouponsBatchDTO;
import com.entity.CouponsBatch;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Response<CouponsBatch> couponsBatch(@RequestBody CouponsBatchDTO couponsBatchDTO){
        CouponsBatch couponsBatch = couponsBatchBussiness.createCouponsBatch(couponsBatchDTO);
        return new Response<>(ResponseCode.SUCCESS, couponsBatch);
    }
}
