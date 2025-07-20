package com.jelee.librarymanagementsystem.domain.user.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.user.dto.JoinRequest;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  
  // 회원가입
  public Long signUp(JoinRequest request) {

    // 아이디 중복 체크
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new BaseException(ErrorCode.USER_USERNAME_DUPLICATED);
    }
    
    // 이메일 중복 체크
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new BaseException(ErrorCode.USER_EMAIL_DUPLICATED);
    }

    User user = User.builder()
              .username(request.getUsername())
              .password(passwordEncoder.encode(request.getPassword()))
              .email(request.getEmail())
              .role(Role.ROLE_USER)
              .joinDate(LocalDateTime.now())
              .build();
    
    return userRepository.save(user).getId();
  }
}
