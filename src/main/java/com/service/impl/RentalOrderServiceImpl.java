package com.service.impl;

import com.dto.OrderDTO;
import com.entity.*;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.RentalOrderMapper;
import com.service.IRentalOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.service.IUserService;
import com.service.OfficeService;
import com.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
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
}
