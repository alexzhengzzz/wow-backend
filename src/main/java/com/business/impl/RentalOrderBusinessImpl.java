package com.business.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.business.RentalOrderBusiness;
import com.dto.OrderCompleteDTO;
import com.dto.OrderDTO;
import com.entity.*;
import com.enums.VehicleStatus;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.ICarClassService;
import com.service.ICouponsBatchService;
import com.service.IRentalOrderService;
import com.service.InvoiceService;
import com.service.impl.*;
import com.utils.cache.TimestampUtil;
import com.vo.orders.OrderInvoiceVO;
import com.vo.orders.OrderListVO;
import com.vo.orders.OrderVO;
import com.vo.orders.OrderVehicleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
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
    private ICarClassService carClassService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ICouponsBatchService couponsBatchService;

    @Autowired
    private ModelServiceImpl modelService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    // only create order with basic info
    public RentalOrder initOrder(OrderDTO orderDTO) {
        checkAndUpdateParams(orderDTO);
        RentalOrder rentalOrder = setNewRentalOrder(orderDTO);
        Boolean isSuccess = rentalOrderService.save(rentalOrder);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "init rental order failed");
        }
        return rentalOrder;

    }

    private BigDecimal getDiscountByCouponId(Long couponId) {
        if (couponId == null) {
            return BigDecimal.ONE;
        }
        Coupons coupons = couponsService.getById(couponId);
        CouponsBatch couponsBatch = null;
        if (coupons != null) {
            couponsBatch = couponsBatchService.getOne(new LambdaQueryWrapper<CouponsBatch>().eq(CouponsBatch::getBatchId, coupons.getBatchId()));
        }
        BigDecimal discount = couponsBatch == null ? BigDecimal.ONE : couponsBatch.getDiscount();
        return discount;
    }


    private OrderInvoiceVO getOrderInvoiceVO(RentalOrder rentalOrder, Invoice invoice) {
        OrderInvoiceVO orderInvoiceVO = new OrderInvoiceVO();
        orderInvoiceVO.setInvoice(invoice);
        orderInvoiceVO.setOrder(rentalOrder);
        return orderInvoiceVO;
    }

    private Invoice setNewInvoice(BigDecimal amount) {
        Invoice invoice = new Invoice();
        invoice.setAmount(amount);
        invoice.setInvoiceDate(new Timestamp(System.currentTimeMillis()));
        return invoice;
    }

    /**
     * @param
     * @param
     * @param
     * @return rental rate per day of the rental service and fees for over mileage (if rental service
     * exceeds odometer limits/day). For example, a rental car service of a mid-size car
     * has daily rate of service as $40/day and over mileage fees as $2/mile. If a customer
     * has rental service for 2 days, and odometer limit of 500 miles/day. So rental service
     * has a limitation of total 1000 miles. If this rental service has used 1050 miles, then
     * customer will be charged as 2days*$40 + $2*50 extra miles, totaling to $180.
     */
//    private List<BigDecimal> calAmount(RentalOrder rentalOrder, BigDecimal discount) {
//        Vehicle vehicle = vehicleService.getVehicleById(rentalOrder.getVinId());
//        CarClass carClass = carClassService.getCarClassInfoById(vehicle.getClassId());
//        List<BigDecimal> amountList;
//        BigDecimal rentalRatePerDay = carClass.getRentalRatePerDay();
//        BigDecimal overFee = carClass.getOverFee();
//        BigDecimal start = rentalOrder.getStartOdometer();
//        BigDecimal end = rentalOrder.getEndOdometer();
//        Long days = TimestampUtil.getDiffDays(rentalOrder.getDropDate(), rentalOrder.getPickDate());
//        BigDecimal limit = rentalOrder.getDailyLimitOdometer();
//        BigDecimal totalOdometer = end.subtract(start);
//        BigDecimal expectedOdometer = BigDecimal.valueOf(days).multiply(limit);
//        BigDecimal overMileage = totalOdometer.subtract(expectedOdometer);
//        if (overMileage.compareTo(BigDecimal.ZERO) > 0) {
//            BigDecimal overMileageFee = overMileage.multiply(overFee).multiply(discount);
//            BigDecimal normalFee = totalOdometer.multiply(rentalRatePerDay).multiply(discount);
//            BigDecimal totalFee = normalFee.add(overMileageFee);
//            amountList = Arrays.asList(normalFee, overMileageFee, totalFee);
//            return amountList;
//        }
//        BigDecimal totalFee = BigDecimal.valueOf(days).multiply(rentalRatePerDay).multiply(discount);
//        amountList = Arrays.asList(totalFee, BigDecimal.ZERO, totalFee);
//        return amountList;
//    }

    private List<BigDecimal> calAmount(RentalOrder rentalOrder, BigDecimal discount) {
        Vehicle vehicle = vehicleService.getVehicleById(rentalOrder.getVinId());
        CarClass carClass = carClassService.getCarClassInfoById(vehicle.getClassId());
        List<BigDecimal> amountList;
        // basic rate & over rate
        BigDecimal rentalRatePerDay = carClass.getRentalRatePerDay();
        BigDecimal overFee = carClass.getOverFee();
        // total days
        BigDecimal start = rentalOrder.getStartOdometer();
        BigDecimal end = rentalOrder.getEndOdometer();
        Long days = TimestampUtil.getDiffDays(rentalOrder.getDropDate(), rentalOrder.getPickDate());
        // over mile
        BigDecimal limit = rentalOrder.getDailyLimitOdometer();
        BigDecimal totalOdometer = end.subtract(start);
        BigDecimal expectedOdometer = BigDecimal.valueOf(days).multiply(limit);
        BigDecimal overMileage = totalOdometer.subtract(expectedOdometer);
        // fee calculation
        BigDecimal baseFee = BigDecimal.valueOf(days).multiply(rentalRatePerDay).multiply(discount);
        BigDecimal totalFee = baseFee;
        BigDecimal overMileageFee = BigDecimal.ZERO;
        if (overMileage.compareTo(BigDecimal.ZERO) > 0) {
            overMileageFee = overMileage.multiply(overFee).multiply(discount);
            totalFee = baseFee.add(overMileageFee);
        }
        amountList = Arrays.asList(baseFee, overMileageFee, totalFee);
        return amountList;
    }


    @Override
    public List<OrderListVO> getOrderByUserId(Long userId) {
        List<RentalOrder> orders = rentalOrderService.list(new LambdaQueryWrapper<RentalOrder>().eq(RentalOrder::getUserId, userId));
        if (orders == null || orders.isEmpty()) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "no order found");
        }
        return convertToOrderVOs(orders);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderInvoiceVO completeOrder(Long orderId, OrderCompleteDTO orderCompleteDTO) {
        // check
        Office dropLoc = officeService.getLocById(orderCompleteDTO.getDropLocId());
        RentalOrder rentalOrder = rentalOrderService.getOrderByOrderAndUserId(orderId, orderCompleteDTO.getUserId());
        if (rentalOrder.getOrderStatus() >= 1) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "order has been completed");
        }
        rentalOrder.setDropLocId(orderCompleteDTO.getDropLocId());
        rentalOrder.setEndOdometer(orderCompleteDTO.getEndOdometer());
        rentalOrder.setDropDate(orderCompleteDTO.getDropDate());
        // cal discount
        BigDecimal discount = getDiscountByCouponId(rentalOrder.getCouponId());
        List<BigDecimal> bigDecimals = calAmount(rentalOrder, discount);
        rentalOrder.setBasicCost(bigDecimals.get(0));
        rentalOrder.setExtraCost(bigDecimals.get(1));
        // set invoice
        Invoice invoice = setNewInvoice(bigDecimals.get(0).add(bigDecimals.get(1)));
        invoiceService.save(invoice);
        // set vehicle status
        vehicleService.updateVehicleStatus(rentalOrder.getVinId(), VehicleStatus.IN_STOCK);
        // set status
        rentalOrder.setInvoiceId(invoice.getInvoiceId());
        rentalOrder.setOrderStatus(1); // finished
        rentalOrderService.updateById(rentalOrder);
        return getOrderInvoiceVO(rentalOrder, invoice);

    }

    private List<OrderListVO> convertToOrderVOs(List<RentalOrder> orders) {
        List<OrderListVO> orderVOs = new ArrayList<>();
        orders.forEach(order -> {
            orderVOs.add(getRentalOrder(order));
        });
        return orderVOs;
    }



    // check odometer and date
    private void checkOrderAndSetParams(OrderDTO orderDTO) {
        if (orderDTO.getStartOdometer().compareTo(orderDTO.getEndOdometer()) > 0) {
            throw GeneralExceptionFactory.create(ErrorCode.ILLEGAL_DATA, "start odometer must less than end odometer");
        }
        if (DateUtil.compare(orderDTO.getPickDate(), orderDTO.getDropDate()) > 0 || DateUtil.compare(orderDTO.getPickDate(), new Timestamp(System.currentTimeMillis())) < 0) {
            throw GeneralExceptionFactory.create(ErrorCode.ILLEGAL_DATA, "pick date must be between now and drop date");
        }
    }

    public void checkAndUpdateParams(OrderDTO orderDTO) {
        if (DateUtil.compare(orderDTO.getPickDate(), new Timestamp(System.currentTimeMillis())) < 0) {
            throw GeneralExceptionFactory.create(ErrorCode.ILLEGAL_DATA, "order date should after today");
        }
        if (orderDTO.getStartOdometer().compareTo(BigDecimal.ZERO) < 0) {
            throw GeneralExceptionFactory.create(ErrorCode.ILLEGAL_DATA, "order date should before today");
        }
        Vehicle vehicle = vehicleService.getVehicleById(orderDTO.getVinId());
        userService.getUserById(orderDTO.getUserId());
        Coupons coupons = couponsService.getById(orderDTO.getCouponId());
        if (coupons != null) {
            if (coupons.getIsUsed()) {
                throw GeneralExceptionFactory.create(ErrorCode.UNKNOWN_ERROR, "the coupon is invalid");
            }
            // set coupon status
            coupons.setIsUsed(true);
            Boolean isSuccess = couponsService.updateById(coupons);
            if (!isSuccess) {
                throw GeneralExceptionFactory.create(ErrorCode.UNKNOWN_ERROR, "update coupon status failed");
            }
            Long batchId = coupons.getBatchId();
            CouponsBatch batch = couponsBatchService.getById(batchId);
            if (batch != null || batch.getStock() != null) {
                batch.setStock(batch.getStock() - 1);
                couponsBatchService.updateById(batch);
            }
        }
        // set car status
        if (!vehicle.getStatus().equals(VehicleStatus.IN_STOCK.getStatus())) {
            throw GeneralExceptionFactory.create(ErrorCode.UNKNOWN_ERROR, "the car is out of stock");
        }
        vehicle.setStatus(VehicleStatus.OUT_STOCK.getStatus());
        Boolean res = vehicleService.updateVehicleStatus(orderDTO.getVinId(), VehicleStatus.OUT_STOCK);
        if (!res) {
            throw GeneralExceptionFactory.create(ErrorCode.UNKNOWN_ERROR, "update car status failed");
        }
    }
    //  actually we should set coupon id to be 1, but not
    private RentalOrder setNewRentalOrder(OrderDTO orderDTO) {
        RentalOrder rentalOrder = new RentalOrder();
        rentalOrder.setPickDate(orderDTO.getPickDate());
        rentalOrder.setDropDate(null);
        rentalOrder.setExpectedDate(orderDTO.getExpectedDate());
        rentalOrder.setStartOdometer(orderDTO.getStartOdometer());
        rentalOrder.setEndOdometer(null);
        rentalOrder.setDailyLimitOdometer(orderDTO.getDailyLimitOdometer());
        rentalOrder.setVinId(orderDTO.getVinId());
        rentalOrder.setUserId(orderDTO.getUserId());
        rentalOrder.setCouponId(orderDTO.getCouponId());
        rentalOrder.setPickLocId(orderDTO.getPickLocId());
        rentalOrder.setDropLocId(null);
        rentalOrder.setOrderStatus(0);
        return rentalOrder;
    }

    private OrderListVO getRentalOrder(RentalOrder rentalOrder) {
        OrderListVO orderListVO = new OrderListVO();
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(rentalOrder.getOrderId());
        orderVO.setUserId(rentalOrder.getUserId());
        orderVO.setVinId(rentalOrder.getVinId());
        orderVO.setPickLocId(rentalOrder.getPickLocId());
        orderVO.setDropLocId(rentalOrder.getDropLocId());
        orderVO.setCouponId(rentalOrder.getCouponId());
        orderVO.setPickDate(rentalOrder.getPickDate());
        orderVO.setDropDate(rentalOrder.getDropDate());
        orderVO.setStartOdometer(rentalOrder.getStartOdometer());
        orderVO.setEndOdometer(rentalOrder.getEndOdometer());
        orderVO.setDailyLimitOdometer(rentalOrder.getDailyLimitOdometer());
        orderVO.setBasicCost(rentalOrder.getBasicCost());
        orderVO.setExtraCost(rentalOrder.getExtraCost());
        orderVO.setExpectedDate(rentalOrder.getExpectedDate());
        orderVO.setOrderStatus(rentalOrder.getOrderStatus());
        orderVO.setInvoiceId(rentalOrder.getInvoiceId());
        orderListVO.setOrderVO(orderVO);
        OrderVehicleVO orderVehicleVO = new OrderVehicleVO();
        orderListVO.setInvoiceId(rentalOrder.getInvoiceId());
        Vehicle vehicle = null;
        try {
            vehicle = vehicleService.getVehicleById(rentalOrder.getVinId());
        } catch (Exception e) {
            log.warn("car not found" + e.getMessage() + rentalOrder.getVinId());
            return orderListVO;
        }
        Integer classId = vehicle.getClassId();
        CarClass carClass = carClassService.getCarClassInfoById(classId);
        Model model = modelService.getById(vehicle.getModelId());
        orderVehicleVO.setName(model.getModelName());
        orderVehicleVO.setVinId(vehicle.getVinId());
        orderVehicleVO.setImgUrl(carClass.getImageUrl());
        orderListVO.setOrderVehicleVO(orderVehicleVO);
        return orderListVO;
    }
}
