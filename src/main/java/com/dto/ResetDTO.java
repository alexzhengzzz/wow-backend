package com.dto;

import lombok.Data;

/**
 * @author alexzhengzzz
 * @date 5/4/22 16:26
 */
@Data
public class ResetDTO {
    private String email;
    private String oldPassword;
    private String newPassword;
    private String secret;

}
