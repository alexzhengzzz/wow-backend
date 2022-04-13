package com.vo;

import lombok.Data;

@Data
public class UserVO {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String password;
}
