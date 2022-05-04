package com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RegisterDTO {
    @NotBlank(message = "first name not blank")
    private String fname;
    @NotBlank(message = "last name not blank")
    private String lname;
    @NotBlank(message = "email not blank")
    private String email;
    @NotBlank(message = "phone not blank")
    private String phoneNum;
    @NotBlank(message = "password not blank")
    private String password;
    @NotNull(message = "role not null")
    private Character role_type;
    private String bakEmail;
    private RegisterUserAddressDTO userAddress;
    private RegisterIndividualDTO individual;
    private RegisterCorporDTO corporate;
}
