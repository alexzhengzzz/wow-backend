package com.controller;

import com.business.RentalOrderBusiness;
import com.dto.OrderCompleteDTO;
import com.dto.OrderDTO;
import com.entity.RentalOrder;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import com.vo.orders.OrderInvoiceVO;
import com.vo.orders.OrderListVO;
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
    public Response<RentalOrder> initOrder(@Valid @RequestBody OrderDTO orderDTO) {
        RentalOrder rentalOrder = rentalOrderBusiness.initOrder(orderDTO);
        return new Response(ResponseCode.SUCCESS, rentalOrder);
    }

//    @ApiOperation("get order by order id")
//    @GetMapping("/{orderId}")
//    public Response<ResponseCode> getOrderByOrderId(@RequestParam("orderId") Long orderId) {
//        return new Response(ResponseCode.SUCCESS, "success");
//    }

    @ApiOperation("complete order")
    @PostMapping("/{orderId}")
    public Response<OrderInvoiceVO> completeOrder(@PathVariable("orderId") Long orderId, @Valid @RequestBody OrderCompleteDTO orderCompleteDTO) {
        OrderInvoiceVO orderInvoiceVO = rentalOrderBusiness.completeOrder(orderId, orderCompleteDTO);
        return new Response(ResponseCode.SUCCESS, orderInvoiceVO);
    }

    @ApiOperation("get order list by user id")
    @GetMapping("/u/{userId}")
    public Response<List<OrderListVO>> getOrderByUserId(@PathVariable("userId") Long userId) {
        return new Response<>(ResponseCode.SUCCESS, rentalOrderBusiness.getOrderByUserId(userId));
    }



}
