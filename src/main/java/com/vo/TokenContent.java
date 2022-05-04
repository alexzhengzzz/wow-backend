package com.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author alexzhengzzz
 * @date 5/3/22 17:15
 */
@Data
public class TokenContent {
    String subject;
    String payload;
    String signature;
    String header;
    Date expireAt;
}
