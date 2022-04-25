package com.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String fname;
    private String lname;
    private String email;
    private String password;
    private Character role_type;
}
