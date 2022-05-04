package com.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.business.PaymentBusiness;
import com.dto.PaymentDTO;
import com.entity.Payment;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.service.InvoiceService;
import com.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
@Service
public class PaymentBusinessImpl implements PaymentBusiness {
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PaymentService paymentService;


    @Override
    public void createPayment(PaymentDTO paymentDTO) {
        checkPaymentDTO(paymentDTO);
        Payment payment = setPayment(paymentDTO);
        Boolean isSuccess = paymentService.save(payment);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.PAYMENT_ERROR, "Payment cannot be created");
        }
    }


    @Override
    public List<Payment> getPaymentByInvoiceId(Long invoiceId) {
        List<Payment> payments = paymentService.list(new LambdaQueryWrapper<Payment>().eq(Payment::getInvoiceId, invoiceId));
        if (payments == null || payments.isEmpty()) {
            throw GeneralExceptionFactory.create(ErrorCode.PAYMENT_ERROR, "Payment not found");
        }
        return payments;
    }

    @Override
    public Payment getPaymentById(Long paymentId) {
        return paymentService.getById(paymentId);
    }


    private Payment setPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setPayAmount(paymentDTO.getPayAmount());
        payment.setPayDate(new Timestamp(System.currentTimeMillis()));
        payment.setInvoiceId(paymentDTO.getInvoiceId());
        payment.setCardNum(paymentDTO.getCardNum());
        payment.setPayMethod(paymentDTO.getPayMethod());
        return payment;
    }

    private void checkPaymentDTO(PaymentDTO paymentDTO) {
        BigDecimal amount = paymentDTO.getPayAmount();
        Long invoiceId = paymentDTO.getInvoiceId();
        invoiceService.getInvoiceById(invoiceId);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw GeneralExceptionFactory.create(ErrorCode.PAYMENT_ERROR, "Payment amount cannot be negative");
        }
    }
}
