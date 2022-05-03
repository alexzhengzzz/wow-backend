package com.business;

import com.dto.OrderDTO;
import com.vo.OrderInvoiceVO;
import com.vo.OrderVO;

import java.util.List;

public interface RentalOrderBusiness {

    OrderInvoiceVO placeOrder(OrderDTO orderDTO);

    List<OrderVO> getOrderByUserId(Long userId);
}
