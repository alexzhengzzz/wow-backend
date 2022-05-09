package com.business.util;

import com.bo.PaymentCardBO;
import com.enums.PaymentCardStatus;
import com.vo.PaymentCardVO;

import java.util.ArrayList;
import java.util.List;

public class PaymentCardBOUtil {

    public static PaymentCardBO  transfer(PaymentCardVO paymentCardVO){
        PaymentCardBO paymentCardBO = new PaymentCardBO();
        paymentCardBO.setCardId(paymentCardVO.getCardId());
        paymentCardBO.setCardNum(paymentCardVO.getCardNum());
        paymentCardBO.setExpireDate(paymentCardVO.getExpireDate());
        paymentCardBO.setLname(paymentCardVO.getLname());
        paymentCardBO.setFname(paymentCardVO.getFname());
        paymentCardBO.setCity(paymentCardVO.getCity());
        paymentCardBO.setState(paymentCardVO.getState());
        paymentCardBO.setCountry(paymentCardVO.getCountry());
        paymentCardBO.setStreet(paymentCardVO.getStreet());
        paymentCardBO.setStatus(paymentCardVO.getStatus());
        paymentCardBO.setUserId(paymentCardVO.getUserId());
        paymentCardBO.setZipcode(paymentCardVO.getZipcode());
        return paymentCardBO;
    }

    public static List<PaymentCardBO> transferList(List<PaymentCardVO> paymentCardVOList){
        List<PaymentCardBO> paymentCardBOList = new ArrayList<>();
        for(PaymentCardVO paymentCardVO:paymentCardVOList){
            if(paymentCardVO.getStatus().equals(PaymentCardStatus.VERIFIED.getStatus()))
            paymentCardBOList.add(transfer(paymentCardVO));
        }
        return paymentCardBOList;
    }

}
