package com.controller;

import com.business.RentalOrderBusiness;
import com.dto.OrderDTO;
import com.enums.ResponseCode;
import com.service.IRentalOrderService;
import com.utils.cache.Response;
import com.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

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
public class RentalOrderController {
    @Autowired
    private RentalOrderBusiness rentalOrderBusiness;
    // place an order
    @PostMapping
    public Response<ResponseCode> placeOrder(@RequestBody OrderDTO orderDTO) {
        rentalOrderBusiness.placeOrder(orderDTO);
        return new Response(ResponseCode.SUCCESS, "success");
    }

    //  list orders by user id
    @GetMapping("/{orderId}")
    public Response<ResponseCode> getOrderByOrderId(@RequestParam("orderId") Long orderId) {
        return new Response(ResponseCode.SUCCESS, "success");
    }

    @GetMapping("/u/{userId}")
    public Response<List<OrderVO>> getOrderByUserId(@PathVariable("userId") Long userId) {
        return new Response(ResponseCode.SUCCESS, rentalOrderBusiness.getOrderByUserId(userId));
    }



}
