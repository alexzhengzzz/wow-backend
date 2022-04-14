package com;

import org.springframework.util.DigestUtils;

public class EncryptHelper {
    public static void main(String[] args) {
        System.out.println(encryptPass("123456"));
    }
    private static String encryptPass(String pass) {
        return DigestUtils.md5DigestAsHex(pass.getBytes());
    }
}
