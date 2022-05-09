package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dto.PaymentCardDTO;
import com.entity.PaymentCard;
import com.vo.PaymentCardVO;

import java.util.List;

public interface IPaymentCardService extends IService<PaymentCard> {

    PaymentCard setPaymentCard(PaymentCardDTO paymentCardDTO);

    List<PaymentCardVO> getCardListByUserId(Long userId);
}
