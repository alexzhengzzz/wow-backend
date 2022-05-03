package com.vo;

import com.bean.LoginUser;
import lombok.Data;

/**
 * @author alexzhengzzz
 * @date 5/3/22 17:06
 */
@Data
public class TokenInfoVO {
    LoginUser loginUser;
    TokenContent Token;
}
