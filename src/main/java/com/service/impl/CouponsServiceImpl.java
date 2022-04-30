package com.service.impl;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dto.CouponCorpDTO;
import com.entity.Corporation;
import com.entity.CouponCust;
import com.entity.Coupons;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.CouponsMapper;
import com.service.ICorporationService;
import com.service.ICouponCustService;
import com.service.ICouponsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.service.IUserService;
import com.utils.cache.TypeInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.entity.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zmh
 * @since 2022-04-29
 */
@Service
public class CouponsServiceImpl extends ServiceImpl<CouponsMapper, Coupons> implements ICouponsService {

    // admion set coupon to company

    // every one can get its own coupon
// admin set coupon to individual customer

    // admin can get coupon by id
}
