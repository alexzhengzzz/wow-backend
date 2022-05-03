package com.business;

import com.dto.PaymentDTO;
import com.entity.Payment;

import java.util.List;

public interface PaymentBusiness {
    void createPayment(PaymentDTO paymentDTO);

    List<Payment> getPaymentByInvoiceId(Long invoiceId);

    Payment getPaymentById(Long paymentId);
}
