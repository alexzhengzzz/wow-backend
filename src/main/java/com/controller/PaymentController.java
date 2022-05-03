package com.controller;

import com.business.PaymentBusiness;
import com.dto.PaymentDTO;
import com.entity.Payment;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
@Slf4j
@Api("payment controller")
@RestController()
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentBusiness paymentBusiness;

    @ApiOperation("get payment by id")
    @GetMapping("/{paymentId}")
    public Response<Payment> getPaymentById(@PathVariable("paymentId") Long paymentId) {
        Payment payment = paymentBusiness.getPaymentById(paymentId);
        return new Response(ResponseCode.SUCCESS, payment);
    }

    @ApiOperation("get payment by invoice id ")
    @GetMapping("/invoice/{invoiceId}")
    public Response<List<Payment>> getPaymentByInvoiceId(@PathVariable("invoiceId") Long invoiceId) {
        return new Response<>(ResponseCode.SUCCESS, paymentBusiness.getPaymentByInvoiceId(invoiceId));
    }

    @ApiOperation("create payment")
    @PostMapping
    public Response createPayment(@RequestBody PaymentDTO paymentDTO) {
        paymentBusiness.createPayment(paymentDTO);
        return new Response(ResponseCode.SUCCESS, "success");
    }
}
