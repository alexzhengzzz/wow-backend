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
        paymentCard.setLname(paymentCardDTO.getLname());
        paymentCard.setFname(paymentCardDTO.getFname());
        paymentCard.setExpireDate(paymentCardDTO.getExpireDate());
        paymentCard.setUserId(paymentCardDTO.getUserId());
        paymentCard.setCountry(paymentCardDTO.getCountry());
        paymentCard.setState(paymentCardDTO.getState());
        paymentCard.setCity(paymentCardDTO.getCity());
        paymentCard.setStreet(paymentCardDTO.getStreet());
        paymentCard.setZipcode(paymentCardDTO.getZipcode());
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
        paymentCardVO.setLname(paymentCard.getLname());
        paymentCardVO.setFname(paymentCard.getFname());

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
