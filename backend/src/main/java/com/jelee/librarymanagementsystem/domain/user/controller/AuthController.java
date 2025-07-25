package com.jelee.librarymanagementsystem.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.user.dto.JoinRequest;
import com.jelee.librarymanagementsystem.domain.user.dto.LoginRequest;
import com.jelee.librarymanagementsystem.domain.user.service.AuthService;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  
  private final AuthService authService;
  private final MessageProvider messageProvider;

  // 회원가입 api
  @PostMapping("/signup")
  public ResponseEntity<ApiResponse> singUp(@RequestBody JoinRequest request) {
    Long userId = authService.signUp(request);
    String successMessage = messageProvider.getMessage("signup.success");
    return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(new ApiResponse("SUCCESS", successMessage, userId));
  }

  // 로그인 api
  @PostMapping("/signin")
  public ResponseEntity signIn(@RequestBody LoginRequest request) {
    Long userId = authService.signIn(request);
    return ResponseEntity.ok(userId);
  }
}
