package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.PaymentCard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaymentCardMapper extends BaseMapper<PaymentCard> {
    List<PaymentCard> selectCardListByUserId(@Param("userId") Long userId);
}
