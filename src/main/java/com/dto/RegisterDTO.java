package com.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Character role_type;
}
