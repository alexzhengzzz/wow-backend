package com;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
@EnableTransactionManagement
@MapperScan("com.mapper")
@EnableAspectJAutoProxy
public class WowBackendApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WowBackendApplication.class, args);
    }
}
