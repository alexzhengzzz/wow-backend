package com.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String password;
}
