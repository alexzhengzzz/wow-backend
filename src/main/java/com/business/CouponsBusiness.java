package com.business;

import com.dto.CouponCorpDTO;
import com.dto.CouponIndividualDTO;

import com.vo.CouponWithOutIdVO;
import com.vo.CouponsVO;

import com.entity.Coupons;

import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CouponsBusiness {
    @Transactional
    List<Coupons> issueCouponsToCorporation(@NotNull CouponCorpDTO couponCorpDTO);

    CouponWithOutIdVO issueCouponsToIndividual(CouponIndividualDTO couponIndividualDTO);


    CouponsVO getValidCouponsByUserId(Long userId);

    @Transactional
    void deleteCouponByCouponId(Long couponId);


    void invalidateCouponById(Long couponId);
}
