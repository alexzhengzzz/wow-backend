package com.mock;

import com.entity.User;
import com.github.javafaker.Faker;
import com.service.IUserService;
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
    IUserService userService;
    @Test
    public void mockUser() {
        int n = 10;
        Faker faker = new Faker(new Locale("en-US"));
        for (int i = 0; i < n; i++) {
            User user = new User();
            user.setId(i + 1000000000000000L);
            user.setFname(faker.funnyName().name());
            user.setPassword(UUID.randomUUID().toString());
            user.setEmail(faker.internet().emailAddress());
            user.setRoleType(Character.valueOf('1'));
            userService.save(user);
        }
    }
}
