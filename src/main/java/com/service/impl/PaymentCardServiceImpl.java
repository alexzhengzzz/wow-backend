package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dto.PaymentCardDTO;
import com.entity.PaymentCard;
import com.mapper.PaymentCardMapper;
import com.service.IPaymentCardService;
import com.service.util.PaymentCardUtil;
import com.vo.PaymentCardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentCardServiceImpl extends ServiceImpl<PaymentCardMapper, PaymentCard> implements IPaymentCardService {

    @Autowired
    PaymentCardMapper paymentCardMapper;

    @Override
    public void setPaymentCard(PaymentCardDTO paymentCardDTO) {
        PaymentCard paymentCard = PaymentCardUtil.transferDTO(paymentCardDTO);
        this.baseMapper.insert(paymentCard);
    }

    @Override
    public List<PaymentCardVO> getCardListByUserId(Long userId) {
        return PaymentCardUtil.transferList(paymentCardMapper.selectCardListByUserId(userId));
    }
}
