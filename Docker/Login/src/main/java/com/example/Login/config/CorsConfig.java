package com.example.Login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200") // 允许访问的前端域名
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的 HTTP 方法
                        .allowedHeaders("*") // 允许的请求头
                        .allowCredentials(true); // 允许发送身份凭证（如 cookies）
            }
        };
    }
}

