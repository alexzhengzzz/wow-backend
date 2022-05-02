package com.business;

import com.dto.OrderDTO;
import com.vo.OrderVO;

import java.util.List;

public interface RentalOrderBusiness {

    void placeOrder(OrderDTO orderDTO);

    List<OrderVO> getOrderByUserId(Long userId);
}
