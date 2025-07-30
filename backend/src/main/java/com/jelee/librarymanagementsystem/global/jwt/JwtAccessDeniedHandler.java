package com.jelee.librarymanagementsystem.global.jwt;

import java.io.IOException;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;
  private final MessageSource messageSource;

  public JwtAccessDeniedHandler (ObjectMapper objectMapper, MessageSource messageSource) {
    this.objectMapper = objectMapper;
    this.messageSource = messageSource;
  }

  @Override
  public void handle(HttpServletRequest request,
                    HttpServletResponse response,
                    AccessDeniedException accessDeniedException) throws IOException {
    
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    String message = messageSource.getMessage("error.forbidden", null, Locale.getDefault());
    ApiResponse<?> apiResponse = ApiResponse.error("AUTH_403", message, null);

    response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
  }
}
