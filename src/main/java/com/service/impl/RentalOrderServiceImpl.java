package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.RentalOrder;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.RentalOrderMapper;
import com.service.IRentalOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author com
 * @since 2022-05-02
 */
@Service
public class RentalOrderServiceImpl extends ServiceImpl<RentalOrderMapper, RentalOrder> implements IRentalOrderService {
    public RentalOrder getOrderById(Long id) {
        RentalOrder order = this.getById(id);
        if (order == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "order id not found");
        }
        return order;
    }

    @Override
    public RentalOrder getOrderByOrderAndUserId(Long orderId, Long userId) {
        RentalOrder order = this.getById(orderId);
        if (order == null || !order.getUserId().equals(userId)) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "order id not found");
        }
        return order;
    }
}
