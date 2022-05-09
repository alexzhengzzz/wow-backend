package com.business;

import com.api.dto.CancelBillListDTO;
import com.api.vo.BillStatusVO;
import com.dto.PaymentDTO;
import com.dto.PaymentUnitDTO;
import com.entity.Payment;
import com.utils.cache.Response;

import java.util.List;

public interface PaymentBusiness {
//    void createPayment(PaymentDTO paymentDTO);
//
//    List<Payment> getPaymentByInvoiceId(Long invoiceId);
//
//    Payment getPaymentById(Long paymentId);

    Payment createPayment(Long invoiceId, PaymentUnitDTO paymentUnitDTO);

    BillStatusVO payWithPayment(Payment payment);

    BillStatusVO withdrawPayment(CancelBillListDTO cancelBillListDTO);

    boolean paymentSucceedUpdateOrderStatus(Long invoiceId);

}
