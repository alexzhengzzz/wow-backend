package com.business.impl;

import com.api.PaySystem;
import com.api.dto.CancelBillListDTO;
import com.api.vo.BillStatusVO;
import com.api.vo.NewBillVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.business.PaymentBusiness;
import com.dto.PaymentCardDTO;
import com.api.dto.PaymentDTO;
import com.dto.PaymentUnitDTO;
import com.entity.Payment;
import com.entity.PaymentCard;
import com.entity.RentalOrder;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.IPaymentCardService;
import com.service.IRentalOrderService;
import com.service.InvoiceService;
import com.service.PaymentService;
import com.service.util.PaymentCardUtil;
import com.utils.cache.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
@Service
public class PaymentBusinessImpl implements PaymentBusiness {
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    IPaymentCardService paymentCardService;

    @Autowired
    PaySystem paySystem;

    @Autowired
    IRentalOrderService rentalOrderService;


//    @Override
//    public void createPayment(PaymentDTO paymentDTO) {
//        checkPaymentDTO(paymentDTO);
//        Payment payment = setPayment(paymentDTO);
//        Boolean isSuccess = paymentService.save(payment);
//        if (!isSuccess) {
//            throw GeneralExceptionFactory.create(ErrorCode.PAYMENT_ERROR, "Payment cannot be created");
//        }
//    }


//    @Override
//    public List<Payment> getPaymentByInvoiceId(Long invoiceId) {
//        List<Payment> payments = paymentService.list(new LambdaQueryWrapper<Payment>().eq(Payment::getInvoiceId, invoiceId));
//        if (payments == null || payments.isEmpty()) {
//            throw GeneralExceptionFactory.create(ErrorCode.PAYMENT_ERROR, "Payment not found");
//        }
//        return payments;
//    }

//    @Override
//    public Payment getPaymentById(Long paymentId) {
//        return paymentService.getById(paymentId);
//    }

    @Override
    public Payment createPayment(Long invoiceId, PaymentUnitDTO paymentUnitDTO) {
        Long cardId = paymentUnitDTO.getCardId();
        //Long invoiceId = paymentUnitDTO.getPayAmount();
        PaymentCardDTO paymentCardDTO = paymentUnitDTO.getPaymentCardDTO();
        if(cardId == null){
            PaymentCard paymentCard = paymentCardService.setPaymentCard(paymentCardDTO);
            cardId = paymentCard.getCardId();
        }
        Payment payment = paymentService.insertPayment(invoiceId, cardId, paymentUnitDTO);
        return payment;
    }

    @Override
    public BillStatusVO payWithPayment(Payment payment){
        // collection payment information
        //Payment payment = paymentService.getById(paymentId);
        Long cardId = payment.getCardId();
        PaymentCard paymentCard = paymentCardService.getById(cardId);
        PaymentDTO paymentDTO = transferPayment(paymentCard, payment);


        ObjectMapper objectMapper = new ObjectMapper();
        // pay with card
        Response<NewBillVO> bill = paySystem.startOnePayment(paymentDTO);
        NewBillVO newBillVO = objectMapper.convertValue(bill.getData(), new TypeReference<NewBillVO>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Long billId = newBillVO.getBillId();
        Response<BillStatusVO> payRes =  paySystem.payByBillId(billId);
        BillStatusVO billStatusVO = objectMapper.convertValue(payRes.getData(), new TypeReference<BillStatusVO>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
         // Integer res = payRes.getData().getStatus();
        return billStatusVO;
    }

    @Override
    public BillStatusVO withdrawPayment(CancelBillListDTO cancelBillListDTO) {
           Response<BillStatusVO> res = paySystem.rollBackAllBills(cancelBillListDTO);
           return res.getData();
    }

    @Override
    public boolean paymentSucceedUpdateOrderStatus(Long invoiceId) {
        RentalOrder rentalOrder = rentalOrderService.PaymentSucceed(invoiceId);
        return rentalOrder != null;
    }

    public PaymentDTO transferPayment(PaymentCard paymentCard, Payment payment){
        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setAmount(payment.getPayAmount());
        paymentDTO.setCardNum(paymentCard.getCardNum());
        paymentDTO.setCvv(111);

        paymentDTO.setCountry(paymentCard.getCountry());
        paymentDTO.setState(paymentCard.getState());
        paymentDTO.setZipcode(paymentCard.getZipcode());
        paymentDTO.setLname(paymentCard.getLname());
        paymentDTO.setFname(paymentCard.getLname());
        paymentDTO.setCity(paymentCard.getCity());
        paymentDTO.setExpiredDate(paymentCard.getExpireDate());

        return paymentDTO;
    }



//    private Payment setPayment(PaymentDTO paymentDTO) {
//        Payment payment = new Payment();
//        payment.setPayAmount(paymentDTO.getPayAmount());
//        payment.setPayDate(new Timestamp(System.currentTimeMillis()));
//        payment.setInvoiceId(paymentDTO.getInvoiceId());
//        //payment.setCardId(paymentDTO.getInvoiceId());
//        //payment.setCardNum(paymentDTO.getCardNum());
//        //payment.setPayMethod(paymentDTO.getPayMethod());
//        return payment;
//    }
//
//    private void checkPaymentDTO(PaymentDTO paymentDTO) {
//        BigDecimal amount = paymentDTO.getPayAmount();
//        Long invoiceId = paymentDTO.getInvoiceId();
//        invoiceService.getInvoiceById(invoiceId);
//        if (amount.compareTo(BigDecimal.ZERO) < 0) {
//            throw GeneralExceptionFactory.create(ErrorCode.PAYMENT_ERROR, "Payment amount cannot be negative");
//        }
//    }
}
