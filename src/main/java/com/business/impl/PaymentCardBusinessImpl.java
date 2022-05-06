package com.business.impl;

import com.bo.PaymentCardBO;
import com.business.PaymentCardBusiness;
import com.dto.PaymentCardDTO;
import com.service.IPaymentCardService;
import com.business.util.PaymentCardBOUtil;
import com.service.util.PaymentCardUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PaymentCardBusinessImpl implements PaymentCardBusiness {


    @Autowired
    IPaymentCardService paymentCardService;
    @Override
    public void setPaymentCard(PaymentCardDTO paymentCardDTO) {
        //paymentCardService.setPaymentCard(paymentCardDTO);
        paymentCardService.save(PaymentCardUtil.transferDTO(paymentCardDTO));
    }

    @Override
    public List<PaymentCardBO> getCardListByUserId(Long userId) {
        return PaymentCardBOUtil.transferList(paymentCardService.getCardListByUserId(userId)) ;
    }
}
