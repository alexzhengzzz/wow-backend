package com.utils.cache;

public class TypeInfo {
    private final static Character CouponIndividualType = '1';
    private final static Character CouponCorporationType = '2';

    private final static Character AdminRoleType = '0';
    private final static Character IndividualRoleType = '1';
    private final static Character CorporationRoleType = '2';

    public static Character getCouponIndividualType() {
        return CouponIndividualType;
    }

    public static Character getCouponCorporationType() {
        return CouponCorporationType;
    }

    public static Character getAdminRoleType() {
        return AdminRoleType;
    }

    public static Character getIndividualRoleType() {
        return IndividualRoleType;
    }

    public static Character getCorporationRoleType() {
        return CorporationRoleType;
    }
}
