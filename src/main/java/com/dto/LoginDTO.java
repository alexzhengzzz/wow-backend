package com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {
    @NotBlank(message = "email not null")
    private String email;
    @NotBlank(message = "password not null")
    private String password;
}
