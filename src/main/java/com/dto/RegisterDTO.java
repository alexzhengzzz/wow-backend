package com.dto;

import com.entity.Corporation;
import com.entity.Individual;
import com.entity.UserAddress;
import lombok.Data;

@Data
public class RegisterDTO {
    private String fname;
    private String lname;
    private String email;
    private String password;
    private Character role_type;
    private RegisterUserAddressDTO userAddress;
    private RegisterIndividualDTO individual;
    private RegisterCorporDTO corporate;
}
