package com.service.impl;

import com.entity.Payment;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.PaymentMapper;
import com.service.PaymentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {

    @Override
    public Payment getPaymentById(Long id) {
        Payment payment = this.getPaymentById(id);
        if (payment == null) {
            throw GeneralExceptionFactory.create(ErrorCode.PAYMENT_ERROR, "payment not found");
        }
        return payment;
    }
}
