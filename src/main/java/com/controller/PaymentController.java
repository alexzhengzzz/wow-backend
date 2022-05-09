package com.controller;

import com.api.dto.CancelBillListDTO;
import com.api.vo.BillStatusVO;
import com.business.PaymentBusiness;
import com.dto.PaymentUnitDTO;
import com.dto.PaymentListDTO;
import com.entity.Payment;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @ApiOperation("check with invoiceId and card info")
    @PostMapping("/{invoiceId}")
    public Response<BillStatusVO> payBill(@PathVariable("invoiceId") Long invoiceId,
                                          @RequestBody PaymentListDTO paymentListDTO){

        CancelBillListDTO finishedBill = new CancelBillListDTO();

        List<PaymentUnitDTO> paymentUnitDTOList =  paymentListDTO.getPaymentUnitDTOList();

        List<Long> billIdList = new ArrayList<>();
        finishedBill.setBillIds(billIdList);

        BillStatusVO billStatusVO = null;
        for(PaymentUnitDTO paymentUnitDTO:paymentUnitDTOList){
            Payment payment = paymentBusiness.createPayment(invoiceId,paymentUnitDTO);
            billStatusVO = paymentBusiness.payWithPayment(payment);
            if(billStatusVO.getStatus() == 0){
                paymentBusiness.withdrawPayment(finishedBill);
                break;
            }else{
                billIdList.add(billStatusVO.getBillId());
            }
        }
        Response<BillStatusVO> res = new Response<>();
        if(billStatusVO.getStatus() == 0){
            return new Response<>(ResponseCode.SYSTEM_ERROR,billStatusVO);
        }else{
            if(paymentBusiness.paymentSucceedUpdateOrderStatus(invoiceId))
                return new Response<>(ResponseCode.SUCCESS, billStatusVO);
            else
                return new Response<>(ResponseCode.ORDER_STATUS_ERROR, billStatusVO);
        }
    }

//    @ApiOperation("get payment by id")
//    @GetMapping("/{paymentId}")
//    public Response<Payment> getPaymentById(@PathVariable("paymentId") Long paymentId) {
//        Payment payment = paymentBusiness.getPaymentById(paymentId);
//        return new Response(ResponseCode.SUCCESS, payment);
//    }
//
//    @ApiOperation("get payment by invoice id ")
//    @GetMapping("/invoice/{invoiceId}")
//    public Response<List<Payment>> getPaymentByInvoiceId(@PathVariable("invoiceId") Long invoiceId) {
//        return new Response<>(ResponseCode.SUCCESS, paymentBusiness.getPaymentByInvoiceId(invoiceId));
//    }
//
//    @ApiOperation("create payment")
//    @PostMapping
//    public Response createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
//        paymentBusiness.createPayment(paymentDTO);
//        return new Response(ResponseCode.SUCCESS, "success");
//    }
//
//    @ApiOperation("pay for the bill")
//    @PostMapping
//    public Response pay(@RequestBody List<PaymentUnitDTO> paymentUnitDTOList){
//
//        return new Response(ResponseCode.SUCCESS, "success");
//    }
}
