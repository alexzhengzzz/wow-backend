package com.business;

import com.dto.CouponCorpDTO;
import com.dto.CouponIndividualDTO;

import com.vo.CouponVO;

import com.entity.Coupons;

import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CouponsBusiness {
    @Transactional
    List<Coupons> issueCouponsToCorporation(@NotNull CouponCorpDTO couponCorpDTO);

    @Transactional
    Coupons issueCouponsToIndividual(CouponIndividualDTO couponIndividualDTO);


    CouponVO getValidCouponsByUserId(Long userId);

    @Transactional
    void deleteCouponByCouponId(Long couponId);
}
