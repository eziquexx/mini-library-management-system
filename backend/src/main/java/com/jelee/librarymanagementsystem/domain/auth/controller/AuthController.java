package com.jelee.librarymanagementsystem.domain.auth.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.auth.dto.JoinReqDTO;
import com.jelee.librarymanagementsystem.domain.auth.dto.JoinResDTO;
import com.jelee.librarymanagementsystem.domain.auth.dto.LoginReqDTO;
import com.jelee.librarymanagementsystem.domain.auth.dto.LogoutResDTO;
import com.jelee.librarymanagementsystem.domain.auth.service.AuthService;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.AuthSuccessCode;
import com.jelee.librarymanagementsystem.global.response.code.UserSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  
  private final AuthService authService;
  private final MessageProvider messageProvider;

  // 공용: 회원가입
  @PostMapping("/signup")
  public ResponseEntity<?> singUp(@RequestBody JoinReqDTO request) {
    
    // 서비스로직
    JoinResDTO resonseDTO = authService.signUp(request);

    // 성공메시지
    String message = messageProvider.getMessage(UserSuccessCode.USER_CREATED.getMessage());
    
    // 응답
    return ResponseEntity
              .status(UserSuccessCode.USER_CREATED.getHttpStatus())
              .body(ApiResponse.success(
                UserSuccessCode.USER_CREATED, 
                message, 
                resonseDTO));
  }

  // 공용: 로그인
  @PostMapping("/signin")
  public ResponseEntity<?> signIn(
    @RequestBody LoginReqDTO request, 
    HttpServletResponse response) {
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

    String message = messageProvider.getMessage(AuthSuccessCode.AUTH_LOGIN_SUCCESS.getMessage());

    return ResponseEntity
              .status(AuthSuccessCode.AUTH_LOGIN_SUCCESS.getHttpStatus())
              .body(ApiResponse.success(
                AuthSuccessCode.AUTH_LOGIN_SUCCESS,
                message, 
                request.getUsername()));
  }

  // 공용: 로그아웃
  @PostMapping("/logout")
  public ResponseEntity<?> logout(
    HttpServletRequest request, 
    HttpServletResponse response) {
    
    // Jwt 제거
    ResponseCookie deleteCookie = ResponseCookie.from("JWT", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0) // 쿠키 즉시 만료
                .sameSite("Strict")
                .build();
    
    response.addHeader("Set-Cookie", deleteCookie.toString());

    LogoutResDTO responseDTO = authService.logout(request);

    String message = messageProvider.getMessage(AuthSuccessCode.AUTH_LOGOUT_SUCCESS.getMessage());

    return ResponseEntity
              .status(AuthSuccessCode.AUTH_LOGOUT_SUCCESS.getHttpStatus())
              .body(ApiResponse.success(
                AuthSuccessCode.AUTH_LOGOUT_SUCCESS, 
                message, 
                responseDTO));
  }
}
