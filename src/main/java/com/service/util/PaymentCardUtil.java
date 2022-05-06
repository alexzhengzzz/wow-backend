package com.service.util;

import com.dto.PaymentCardDTO;
import com.entity.PaymentCard;
import com.vo.PaymentCardVO;

import java.util.ArrayList;
import java.util.List;

public class PaymentCardUtil {
    public static PaymentCard transferDTO(PaymentCardDTO paymentCardDTO){
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardNum(paymentCardDTO.getCardNum());
        paymentCard.setName(paymentCardDTO.getName());
        paymentCard.setExpireDate(paymentCardDTO.getExpireDate());
        paymentCard.setUserId(paymentCardDTO.getUserId());
        paymentCard.setCountry(paymentCardDTO.getCountry());
        paymentCard.setState(paymentCardDTO.getState());
        paymentCard.setCity(paymentCard.getCity());
        paymentCard.setStreet(paymentCard.getStreet());
        paymentCard.setZipcode(paymentCard.getZipcode());
        paymentCard.setStatus("V");
        return paymentCard;
    }

    public static PaymentCardVO transfer(PaymentCard paymentCard){
        PaymentCardVO paymentCardVO = new PaymentCardVO();

        paymentCardVO.setCardId(paymentCard.getCardId());
        paymentCardVO.setCardNum(paymentCard.getCardNum());
        paymentCardVO.setExpireDate(paymentCard.getExpireDate());
        paymentCardVO.setUserId(paymentCard.getUserId());
        paymentCardVO.setStatus(paymentCard.getStatus());
        paymentCardVO.setName(paymentCard.getName());

        paymentCardVO.setCountry(paymentCard.getCountry());
        paymentCardVO.setCity(paymentCard.getCity());
        paymentCardVO.setState(paymentCard.getState());
        paymentCardVO.setStreet(paymentCard.getStreet());
        paymentCardVO.setZipcode(paymentCard.getZipcode());

        return paymentCardVO;
    }

    public static List<PaymentCardVO> transferList(List<PaymentCard> paymentCardList){
        List<PaymentCardVO> paymentCardVOList = new ArrayList<>();
        for(PaymentCard paymentCard:paymentCardList){
            paymentCardVOList.add(transfer(paymentCard));
        }
        return paymentCardVOList;
    }

}
