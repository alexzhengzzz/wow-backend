package com.mock;

import com.controller.LoginController;
import com.entity.User;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.javafaker.Faker;
import com.service.UserService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;
import java.util.UUID;

@MapperScan(basePackages = "com.mapper")
@SpringBootTest
public class MockUser {
    @Autowired
    UserService userService;
    @Test
    public void mockUser() {
        int n = 10;
        Faker faker = new Faker(new Locale("en-US"));
        for (int i = 0; i < n; i++) {
            User user = new User();
            user.setId(i + 1000000000000000L);
            user.setName(faker.funnyName().name());
            user.setPassword(UUID.randomUUID().toString());
            user.setEmail(faker.internet().emailAddress());
            user.setRole_type(Character.valueOf('1'));
            userService.save(user);
        }
    }
}
