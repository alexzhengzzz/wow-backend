package com.business;

import com.dto.OrderCompleteDTO;
import com.dto.OrderDTO;
import com.vo.orders.OrderInvoiceVO;
import com.vo.orders.OrderListVO;

import java.util.List;

public interface RentalOrderBusiness {

    OrderInvoiceVO initOrder(OrderDTO orderDTO);

    List<OrderListVO> getOrderByUserId(Long userId);

    OrderInvoiceVO completeOrder(Long orderId, OrderCompleteDTO orderCompleteDTO);
}
