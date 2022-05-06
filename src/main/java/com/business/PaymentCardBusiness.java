package com.business;

import com.bo.PaymentCardBO;
import com.dto.PaymentCardDTO;
import com.entity.PaymentCard;

import java.util.List;

public interface PaymentCardBusiness {
    void setPaymentCard(PaymentCardDTO paymentCardDTO);

    List<PaymentCardBO> getCardListByUserId(Long userId);

}
