package com.business.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.business.RentalOrderBusiness;
import com.dto.OrderDTO;
import com.entity.*;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.*;
import com.service.impl.CouponsServiceImpl;
import com.service.impl.OfficeServiceImpl;
import com.service.impl.UserServiceImpl;
import com.service.impl.VehicleServiceImpl;
import com.utils.cache.TimestampUtil;
import com.vo.OrderInvoiceVO;
import com.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    private CarClassService carClassService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ICouponsBatchService couponsBatchService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderInvoiceVO placeOrder(OrderDTO orderDTO) {
        checkOrderAndSetParams(orderDTO);

        Vehicle vehicle = vehicleService.getVehicleById(orderDTO.getVinId());
        User user = userService.getUserById(orderDTO.getUserId());
        Coupons coupons = couponsService.getById(orderDTO.getCouponId());
        CouponsBatch couponsBatch = null;
        if (coupons != null) {
            couponsBatch = couponsBatchService.getOne(new LambdaQueryWrapper<CouponsBatch>().eq(CouponsBatch::getBatchId, coupons.getBatchId()));
        }
        BigDecimal discount = couponsBatch == null ? BigDecimal.ONE : couponsBatch.getDiscount();
        Office pickLoc = officeService.getLocById(orderDTO.getPickLocId());
        Office dropLoc = officeService.getLocById(orderDTO.getPickLocId());

        BigDecimal amount = calAmount(orderDTO, vehicle, discount);
        RentalOrder rentalOrder = setNewRentalOrder(orderDTO, vehicle, user, coupons, pickLoc, dropLoc);
        Boolean isSuccess = rentalOrderService.save(rentalOrder);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "save rental order failed");
        }
        Invoice invoice = setNewInvoice(rentalOrder, amount);
        invoiceService.save(invoice);
        return getOrderInvoiceVO(rentalOrder, invoice);

    }



    private OrderInvoiceVO getOrderInvoiceVO(RentalOrder rentalOrder, Invoice invoice) {
        OrderInvoiceVO orderInvoiceVO = new OrderInvoiceVO();
        orderInvoiceVO.setInvoice(invoice);
        orderInvoiceVO.setOrder(rentalOrder);
        return orderInvoiceVO;
    }

    private Invoice setNewInvoice(RentalOrder rentalOrder, BigDecimal amount) {
        Invoice invoice = new Invoice();
        invoice.setAmount(amount);
        invoice.setInvoiceDate(new Timestamp(System.currentTimeMillis()));
        return invoice;
    }

    /**
     * @param orderDTO
     * @param vehicle
     * @param discount
     * @return rental rate per day of the rental service and fees for over mileage (if rental service
     * exceeds odometer limits/day). For example, a rental car service of a mid-size car
     * has daily rate of service as $40/day and over mileage fees as $2/mile. If a customer
     * has rental service for 2 days, and odometer limit of 500 miles/day. So rental service
     * has a limitation of total 1000 miles. If this rental service has used 1050 miles, then
     * customer will be charged as 2days*$40 + $2*50 extra miles, totaling to $180.
     */
    private BigDecimal calAmount(OrderDTO orderDTO, Vehicle vehicle, BigDecimal discount) {
        CarClass carClass = carClassService.getCarClassInfoById(vehicle.getClassId());
        BigDecimal rentalRatePerDay = carClass.getRentalRatePerDay();
        BigDecimal overFee = carClass.getOverFee();
        BigDecimal start = orderDTO.getStartOdometer();
        BigDecimal end = orderDTO.getEndOdometer();
        Long days = TimestampUtil.getDiffDays(orderDTO.getDropDate(), orderDTO.getPickDate());
        BigDecimal limit = orderDTO.getDailyLimitOdometer();
        BigDecimal totalOdometer = end.subtract(start);
        BigDecimal expectedOdometer = BigDecimal.valueOf(days).multiply(limit);
        BigDecimal overMileage = totalOdometer.subtract(expectedOdometer);
        if (overMileage.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal overMileageFee = overMileage.multiply(overFee);
            BigDecimal normalFee = expectedOdometer.multiply(rentalRatePerDay);
            return normalFee.add(overMileageFee).multiply(discount);
        }
        return BigDecimal.valueOf(days).multiply(rentalRatePerDay).multiply(discount);
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


    private void checkOrderAndSetParams(OrderDTO orderDTO) {
        if (orderDTO.getStartOdometer().compareTo(orderDTO.getEndOdometer()) > 0) {
            throw GeneralExceptionFactory.create(ErrorCode.ILLEGAL_DATA, "start odometer must less than end odometer");
        }
        if (DateUtil.compare(orderDTO.getPickDate(), orderDTO.getDropDate()) > 0 || DateUtil.compare(orderDTO.getPickDate(), new Timestamp(System.currentTimeMillis())) < 0) {
            throw GeneralExceptionFactory.create(ErrorCode.ILLEGAL_DATA, "pick date must be between now and drop date");
        }
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
