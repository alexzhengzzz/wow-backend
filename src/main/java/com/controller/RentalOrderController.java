package com.controller;

import com.business.RentalOrderBusiness;
import com.dto.OrderDTO;
import com.enums.ResponseCode;
import com.utils.cache.Response;
import com.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RestController
@RequestMapping("/api/order")
@Api("order controller")
public class RentalOrderController {
    @Autowired
    private RentalOrderBusiness rentalOrderBusiness;
    @ApiOperation("place an order")
    @PostMapping
    public Response<ResponseCode> placeOrder(@RequestBody OrderDTO orderDTO) {
        rentalOrderBusiness.placeOrder(orderDTO);
        return new Response(ResponseCode.SUCCESS, "success");
    }

//    @ApiOperation("get order by order id")
//    @GetMapping("/{orderId}")
//    public Response<ResponseCode> getOrderByOrderId(@RequestParam("orderId") Long orderId) {
//        return new Response(ResponseCode.SUCCESS, "success");
//    }

    @ApiOperation("get order list by user id")
    @GetMapping("/u/{userId}")
    public Response<List<OrderVO>> getOrderByUserId(@PathVariable("userId") Long userId) {
        return new Response<>(ResponseCode.SUCCESS, rentalOrderBusiness.getOrderByUserId(userId));
    }



}
