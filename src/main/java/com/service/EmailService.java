package com.service;

/**
 * @author alexzhengzzz
 * @date 5/4/22 17:49
 */
public interface EmailService {
    void sendSimpleMessage(
            String to, String subject, String text);
}
