package com.controller;

import com.business.RentalOrderBusiness;
import com.dto.OrderCompleteDTO;
import com.dto.OrderDTO;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import com.vo.OrderInvoiceVO;
import com.vo.OrderVO;
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
 * @since 2022-05-02
 */
@RestController
@RequestMapping("/api/order")
@Api("order controller")
public class RentalOrderController {
    @Autowired
    private RentalOrderBusiness rentalOrderBusiness;

    @ApiOperation("init an order")
    @PostMapping
    public Response<OrderInvoiceVO> initOrder(@Valid @RequestBody OrderDTO orderDTO) {
        OrderInvoiceVO orderInvoiceVO = rentalOrderBusiness.initOrder(orderDTO);
        return new Response(ResponseCode.SUCCESS, orderInvoiceVO);
    }

//    @ApiOperation("get order by order id")
//    @GetMapping("/{orderId}")
//    public Response<ResponseCode> getOrderByOrderId(@RequestParam("orderId") Long orderId) {
//        return new Response(ResponseCode.SUCCESS, "success");
//    }

    @ApiOperation("complete order")
    @PostMapping("/{orderId}")
    public Response<OrderInvoiceVO> completeOrder(@PathVariable("orderId") Long orderId, @RequestBody OrderCompleteDTO orderCompleteDTO) {

        OrderInvoiceVO orderInvoiceVO = rentalOrderBusiness.completeOrder(orderId, orderCompleteDTO);
        return new Response(ResponseCode.SUCCESS, orderInvoiceVO);
    }

    @ApiOperation("get order list by user id")
    @GetMapping("/u/{userId}")
    public Response<List<OrderVO>> getOrderByUserId(@PathVariable("userId") Long userId) {
        return new Response<>(ResponseCode.SUCCESS, rentalOrderBusiness.getOrderByUserId(userId));
    }



}
