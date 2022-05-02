package com.utils.cache;

/**
 * get character of type
 */
public class TypeInfo {
    // coupon type
    private final static Character COUPON_INDIVIDUAL_TYPE = '1';
    private final static Character COUPON_CORPORATION_TYPE = '2';
    // role type
    private final static Character ADMIN_ROLETYPE = '0';
    private final static Character INDIVIDUAL_ROLETYPE = '1';
    private final static Character CORPORATION_ROLETYPE = '2';

    public static Character getCouponIndividualType() {
        return COUPON_INDIVIDUAL_TYPE;
    }

    public static Character getCouponCorporationType() {
        return COUPON_CORPORATION_TYPE;
    }

    public static Character getAdminRoleType() {
        return ADMIN_ROLETYPE;
    }

    public static Character getIndividualRoleType() {
        return INDIVIDUAL_ROLETYPE;
    }

    public static Character getCorporationRoleType() {
        return CORPORATION_ROLETYPE;
    }
}
