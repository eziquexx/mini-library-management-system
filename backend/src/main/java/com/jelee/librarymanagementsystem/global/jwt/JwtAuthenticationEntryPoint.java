package com.jelee.librarymanagementsystem.global.jwt;

import java.io.IOException;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;
  private final MessageSource messageSource;

  public JwtAuthenticationEntryPoint(ObjectMapper objectMapper, MessageSource messageSource) {
    this.objectMapper = objectMapper;
    this.messageSource = messageSource;
  }

  @Override
  public void commence(HttpServletRequest request,
                      HttpServletResponse response,
                      AuthenticationException authException) throws IOException {
    
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    String message = messageSource.getMessage("error.unauthorized", null, Locale.getDefault());
    ApiResponse<?> apiResponse = ApiResponse.error("AUTH_401", message, null);
System.out.println("[AUTH ENTRY POINT] triggered by: " + authException.getClass());

    response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
  }
}
