package com.config;

import com.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author alexzhengzzz
 * @data 2022/5/3
 * @description: CORS, token interceptor
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // set the login interceptor which can help to use redis bean in the interceptor
    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/admin/**")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/register")
                .excludePathPatterns("/reset");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedOrigins("http://localhost:3000")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("*")
                .maxAge(3600 * 24);
    }
}