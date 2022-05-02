package com.business.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.business.RentalOrderBusiness;
import com.dto.OrderDTO;
import com.entity.*;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.IRentalOrderService;
import com.service.impl.CouponsServiceImpl;
import com.service.impl.OfficeServiceImpl;
import com.service.impl.UserServiceImpl;
import com.service.impl.VehicleServiceImpl;
import com.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class RentalOrderBusinessImpl implements RentalOrderBusiness {
    @Autowired
    private VehicleServiceImpl vehicleService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CouponsServiceImpl couponsService;

    @Autowired
    private OfficeServiceImpl officeService;

    @Autowired
    private IRentalOrderService rentalOrderService;

    @Override
    public void placeOrder(OrderDTO orderDTO) {
        RentalOrder rentalOrder = checkOrderAndSetParams(orderDTO);
        Boolean isSuccess = rentalOrderService.save(rentalOrder);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "save rental order failed");
        }
    }

    @Override
    public List<OrderVO> getOrderByUserId(Long userId) {
        List<RentalOrder> orders = rentalOrderService.list(new LambdaQueryWrapper<RentalOrder>().eq(RentalOrder::getUserId, userId));
        if (orders == null || orders.isEmpty()) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "no order found");
        }
        return convertToOrderVOs(orders);
    }

    private List<OrderVO> convertToOrderVOs(List<RentalOrder> orders) {
        List<OrderVO> orderVOs = new ArrayList<>();
        orders.forEach(order -> {
            orderVOs.add(getRentalOrder(order));
        });
        return orderVOs;
    }

    private OrderVO getRentalOrder(RentalOrder rentalOrder) {
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(rentalOrder.getOrderId());
        orderVO.setUserId(rentalOrder.getUserId());
        orderVO.setVinId(rentalOrder.getVinId());
        orderVO.setPickLocId(rentalOrder.getPickLocId());
        orderVO.setDropLocId(rentalOrder.getDropLocId());
        orderVO.setPickDate(rentalOrder.getPickDate());
        orderVO.setDropDate(rentalOrder.getDropDate());
        orderVO.setStartOdometer(rentalOrder.getStartOdometer());
        orderVO.setEndOdometer(rentalOrder.getEndOdometer());
        orderVO.setDailyLimitOdometer(rentalOrder.getDailyLimitOdometer());
        return orderVO;
    }


    private RentalOrder checkOrderAndSetParams(OrderDTO orderDTO) {
        if (orderDTO.getStartOdometer().compareTo(orderDTO.getEndOdometer()) > 0) {
            throw GeneralExceptionFactory.create(ErrorCode.ILLEGAL_DATA, "start odometer must less than end odometer");
        }
        if (DateUtil.compare(orderDTO.getPickDate(), orderDTO.getDropDate()) > 0 || DateUtil.compare(orderDTO.getPickDate(), new Timestamp(System.currentTimeMillis())) < 0) {
            throw GeneralExceptionFactory.create(ErrorCode.ILLEGAL_DATA, "pick date must be between now and drop date");
        }
        Vehicle vehicle = vehicleService.getVehicleById(orderDTO.getVinId());
        User user = userService.getUserById(orderDTO.getUserId());
        Coupons coupons = orderDTO.getCouponId() == null ? null : couponsService.getById(orderDTO.getCouponId());
        Office pickLoc = officeService.getLocById(orderDTO.getPickLocId());
        Office dropLoc = officeService.getLocById(orderDTO.getPickLocId());
        return setNewRentalOrder(orderDTO, vehicle, user, coupons, pickLoc, dropLoc);
    }

    private RentalOrder setNewRentalOrder(OrderDTO orderDTO, Vehicle vehicle, User user, Coupons coupons, Office pickLoc, Office dropLoc) {
        RentalOrder rentalOrder = new RentalOrder();
        rentalOrder.setPickDate(orderDTO.getPickDate());
        rentalOrder.setDropDate(orderDTO.getDropDate());
        rentalOrder.setStartOdometer(orderDTO.getStartOdometer());
        rentalOrder.setEndOdometer(orderDTO.getEndOdometer());
        rentalOrder.setDailyLimitOdometer(orderDTO.getDailyLimitOdometer());
        rentalOrder.setVinId(orderDTO.getVinId());
        rentalOrder.setUserId(orderDTO.getUserId());
        rentalOrder.setCouponId(orderDTO.getCouponId());
        rentalOrder.setPickLocId(orderDTO.getPickLocId());
        rentalOrder.setDropLocId(orderDTO.getDropLocId());
        return rentalOrder;
    }
}
