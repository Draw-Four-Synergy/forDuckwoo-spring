package com.forDukwoo.timeZip.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 자원 공유 허락할 origin 등록 가능
                .allowedOrigins("http://localhost:5500", "http://000.00.000.000:5500", "http://127.0.0.1:5500", "http://000.00.00.0:5500")
                .allowedMethods("GET", "POST", "PATCH", "DELETE")
                .maxAge(3600);
    }
}