package com.bean;

import com.enums.Role;
import com.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements Serializable {
    // !!! important deserialized version number
    private static final long serialVersionUID = 8841433872811285796L;
    private String email;
    private Role role = Role.ANONYMOUS;
    private RoleType roleType = RoleType.GUEST;
    private Long userId;
}
