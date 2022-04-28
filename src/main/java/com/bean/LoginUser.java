package com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 8841433872811285796L;
    private String email;
    private Character role;
    private Long userId;
}
