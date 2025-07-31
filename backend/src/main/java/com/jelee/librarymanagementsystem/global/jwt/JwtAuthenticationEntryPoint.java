package com.jelee.librarymanagementsystem.global.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jelee.librarymanagementsystem.global.enums.ErrorCode;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;
  private final MessageProvider messageProvider;

  public JwtAuthenticationEntryPoint(ObjectMapper objectMapper, MessageProvider messageProvider) {
    this.objectMapper = objectMapper;
    this.messageProvider = messageProvider;
  }

  @Override
  public void commence(HttpServletRequest request,
                      HttpServletResponse response,
                      AuthenticationException authException) throws IOException {
    
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    // String message = messageSource.getMessage("error.unauthorized", null, Locale.getDefault());
    String message = messageProvider.getMessage(ErrorCode.UNAUTHORIZED.getMessage());
    ApiResponse<?> apiResponse = ApiResponse.error(ErrorCode.UNAUTHORIZED, message, null);

    System.out.println("[AUTH ENTRY POINT] triggered by: " + authException.getClass());

    response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
  }
}
