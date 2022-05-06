package com.controller;

import com.bo.PaymentCardBO;
import com.business.PaymentCardBusiness;
import com.dto.PaymentCardDTO;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api("payment card controller")
@RestController()
@RequestMapping("/api/paymentCard")
public class PaymentCardController {

    @Autowired
    PaymentCardBusiness paymentCardBusiness;

    @ApiOperation("add new payment card")
    @PostMapping("/createCard")
    public Response createPaymentCard(@RequestBody PaymentCardDTO paymentCardDTO){
        paymentCardBusiness.setPaymentCard(paymentCardDTO);
        return new Response(ResponseCode.SUCCESS, "success");
    }

    @ApiOperation("get user's Payment method")
    @GetMapping("/getUserCardList/{userId}")
    public Response<List<PaymentCardBO>> getAllCarList(@PathVariable("userId") Long userId) {
        return new Response<>(ResponseCode.SUCCESS,paymentCardBusiness.getCardListByUserId(userId));
    }
}
