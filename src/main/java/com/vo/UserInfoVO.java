package com.vo;

import lombok.Data;

@Data
public class UserInfoVO {
    private Long userId;
    private String fname;
    private String lname;
    private String email;
    private String phoneNum;
    private Character role_type;
    private UserInfoAddressVO userAddress;
    private UserIndividualVO individual;
    private UserCorporationVO corporate;
}
