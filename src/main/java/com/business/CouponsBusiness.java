package com.business;

import com.dto.CouponCorpDTO;
import com.dto.CouponIndividualDTO;
import com.utils.cache.vo.CouponVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

public interface CouponsBusiness {
    @Transactional
    void issueCouponsToCorporation(@NotNull CouponCorpDTO couponCorpDTO);

    @Transactional
    void issueCouponsToIndividual(CouponIndividualDTO couponIndividualDTO);


    CouponVO getValidCouponsByUserId(Long userId);

    @Transactional
    void deleteCouponByCouponId(Long couponId);
}
