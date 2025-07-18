package com.jelee.librarymanagementsystem.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.user.dto.JoinRequest;
import com.jelee.librarymanagementsystem.domain.user.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  
  private final AuthService authService;

  // 회원가입 api
  @PostMapping("/signup")
  public ResponseEntity<String> singUp(@RequestBody JoinRequest request) {
    Long userId = authService.signUp(request);
    return ResponseEntity
              .status(HttpStatus.CREATED)
              .body("회원가입이 완료되었습니다. userId: " + userId);
  }
}
