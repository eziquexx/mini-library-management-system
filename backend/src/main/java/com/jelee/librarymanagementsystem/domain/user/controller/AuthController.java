package com.jelee.librarymanagementsystem.domain.user.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.user.dto.JoinRequest;
import com.jelee.librarymanagementsystem.domain.user.dto.LoginRequest;
import com.jelee.librarymanagementsystem.domain.user.service.AuthService;
import com.jelee.librarymanagementsystem.global.enums.SuccessCode;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  
  private final AuthService authService;
  private final MessageProvider messageProvider;

  // 회원가입 api
  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<Long>> singUp(@RequestBody JoinRequest request) {
    Long userId = authService.signUp(request);

    String message = messageProvider.getMessage(SuccessCode.USER_CREATED.getMessage());
    
    return ResponseEntity
              .status(SuccessCode.USER_CREATED.getHttpStatus())
              .body(ApiResponse.success(
                SuccessCode.USER_CREATED, 
                message, 
                userId));
  }

  // 로그인 api
  @PostMapping("/signin")
  public ResponseEntity<?> signIn(@RequestBody LoginRequest request, HttpServletResponse response) {
    String token = authService.signIn(request);

    // Jwt를 HttpOnly 쿠키에 저장
    ResponseCookie cookie = ResponseCookie.from("JWT", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Strict")
                .build();

    response.addHeader("Set-Cookie", cookie.toString());

    String message = messageProvider.getMessage(SuccessCode.USER_LOGIN_SUCCESS.getMessage());

    return ResponseEntity
              .status(SuccessCode.USER_LOGIN_SUCCESS.getHttpStatus())
              .body(ApiResponse.success(
                SuccessCode.USER_LOGIN_SUCCESS,
                message, 
                request.getUsername()));
  }
}
