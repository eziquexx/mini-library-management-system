package com.jelee.librarymanagementsystem.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  
  @Override
  public void addCorsMappings(@NonNull CorsRegistry registry) {
    registry.addMapping("/**") // 모든 경로에 대해
            .allowedOrigins("http://localhost:3000") // React 앱 주소 허용
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // 허용 HTTP 메서드
            .allowCredentials(true); // 쿠키 허용 등 인증 관련 설정
  }
}
