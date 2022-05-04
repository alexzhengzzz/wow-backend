package com.business;

import com.dto.OrderCompleteDTO;
import com.dto.OrderDTO;
import com.vo.OrderInvoiceVO;
import com.vo.OrderVO;

import java.util.List;

public interface RentalOrderBusiness {

    OrderInvoiceVO initOrder(OrderDTO orderDTO);

    List<OrderVO> getOrderByUserId(Long userId);

    OrderInvoiceVO completeOrder(Long orderId, OrderCompleteDTO orderCompleteDTO);
}
