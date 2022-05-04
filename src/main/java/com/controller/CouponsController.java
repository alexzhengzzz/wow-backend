package com.controller;

import com.business.CouponsBusiness;
import com.dto.CouponCorpDTO;
import com.dto.CouponIndividualDTO;
import com.entity.Coupons;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import com.vo.CouponWithOutIdVO;
import com.vo.CouponsVO;
import io.swagger.annotations.Api;
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
 * @since 2022-04-29
 */
@RestController
@Api("coupons api")
@RequestMapping("/api/coupons")
public class CouponsController {
    @Autowired
    private CouponsBusiness couponsBusiness;

    @ApiOperation("issue coupons to all the employees of Corporation, will create a new batch")
    @PostMapping("/corporation")
    public Response<List<Coupons>> issueCouponsToCorporation(@Valid @RequestBody CouponCorpDTO couponCorpDTO){
        List<Coupons> res = couponsBusiness.issueCouponsToCorporation(couponCorpDTO);
        return new Response<>(ResponseCode.SUCCESS, res);
    }
    @ApiOperation("issue coupons to Individual, need a batchId")
    @PostMapping("/individual")
    public Response<CouponWithOutIdVO> issueCouponsToIndividual(@Valid @RequestBody CouponIndividualDTO couponIndividualDTO){
        CouponWithOutIdVO coupons = couponsBusiness.issueCouponsToIndividual(couponIndividualDTO);
        return new Response<>(ResponseCode.SUCCESS, coupons);
    }

    @ApiOperation("get valid coupons by userId")
    @GetMapping("/u/{userId}")
    public Response<CouponsVO> getValidCouponsByUserId(@PathVariable("userId") Long userId){
        CouponsVO couponsVO = couponsBusiness.getValidCouponsByUserId(userId);
        return new Response<>(couponsVO);
    }

    @ApiOperation("delete coupons by couponId")
    @DeleteMapping("/{couponId}")
    public Response<ResponseCode> deleteCouponByCouponId(@PathVariable("couponId") Long couponId){
        couponsBusiness.deleteCouponByCouponId(couponId);
        return new Response<>(ResponseCode.SUCCESS);
    }
}
