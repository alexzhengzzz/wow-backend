package com.service;

import com.entity.Payment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
public interface PaymentService extends IService<Payment> {
    Payment getPaymentById(Long id);
}
