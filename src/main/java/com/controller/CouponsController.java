package com.controller;

import com.annotation.PermissionChecker;
import com.business.CouponsBusiness;
import com.dto.CouponCorpDTO;
import com.dto.CouponIndividualDTO;
import com.entity.Individual;
import com.enums.ResponseCode;
import com.service.ICouponsService;
import com.utils.cache.Response;
import com.vo.CouponVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zmh
 * @since 2022-04-29
 */
@RestController
@Api("coupons api")
@RequestMapping("/coupons")
public class CouponsController {
    @Autowired
    private CouponsBusiness couponsBusiness;

    @ApiOperation("issue coupons to Corporation by companyName and discount")
    @PostMapping("/corporation")
    public Response<ResponseCode> issueCouponsToCorporation(@RequestBody CouponCorpDTO couponCorpDTO){
        couponsBusiness.issueCouponsToCorporation(couponCorpDTO);
        return new Response<>(ResponseCode.SUCCESS);
    }
    @ApiOperation("issue coupons to Individual")
    @PostMapping("/individual")
    public Response<ResponseCode> issueCouponsToIndividual(@RequestBody CouponIndividualDTO couponIndividualDTO){
        couponsBusiness.issueCouponsToIndividual(couponIndividualDTO);
        return new Response<>(ResponseCode.SUCCESS);
    }

    @ApiOperation("get valid coupons by userId")
    @GetMapping("/u/{userId}")
    public Response<CouponVO> getValidCouponsByUserId(@PathVariable("userId") Long userId){
        CouponVO couponVO = couponsBusiness.getValidCouponsByUserId(userId);
        return new Response<>(couponVO);
    }

    @ApiOperation("delete coupons by couponId")
    @DeleteMapping("/{couponId}")
    public Response<ResponseCode> deleteCouponByCouponId(@PathVariable("couponId") Long couponId){
        couponsBusiness.deleteCouponByCouponId(couponId);
        return new Response<>(ResponseCode.SUCCESS);
    }


}
