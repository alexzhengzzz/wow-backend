package com.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zmh
 * @since 2022-04-29
 */
@Deprecated
@TableName("coupon_cust")
public class CouponCust implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("coupon_id")
    private String couponCustId;
    private Long couponId;

    private Long custId;

    private Character couponType;


    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Character getCouponType() {
        return couponType;
    }

    public void setCouponType(Character couponType) {
        this.couponType = couponType;
    }

    @Override
    public String toString() {
        return "CouponCust{" +
        "couponId=" + couponId +
        ", custId=" + custId +
        ", couponType=" + couponType +
        "}";
    }
}
