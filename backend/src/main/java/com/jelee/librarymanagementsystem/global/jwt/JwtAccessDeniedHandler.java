package com.jelee.librarymanagementsystem.global.jwt;

import java.io.IOException;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jelee.librarymanagementsystem.global.enums.ErrorCode;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;
  private final MessageProvider messageProvider;

  public JwtAccessDeniedHandler (ObjectMapper objectMapper, MessageProvider messageProvider) {
    this.objectMapper = objectMapper;
    this.messageProvider = messageProvider;
  }

  @Override
  public void handle(HttpServletRequest request,
                    HttpServletResponse response,
                    AccessDeniedException accessDeniedException) throws IOException {
    
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    // String message = messageSource.getMessage("error.forbidden", null, Locale.getDefault());

    String message = messageProvider.getMessage(ErrorCode.FORBIDDEN.getMessage());
    ApiResponse<?> apiResponse = ApiResponse.error(ErrorCode.FORBIDDEN, message, null);

    response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
  }
}
