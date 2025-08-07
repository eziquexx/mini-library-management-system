package com.jelee.librarymanagementsystem.domain.auth.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.auth.dto.JoinRequest;
import com.jelee.librarymanagementsystem.domain.auth.dto.LoginRequest;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.jwt.JwtTokenProvider;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  
  // 회원가입
  public Long signUp(JoinRequest request) {

    // 아이디 중복 체크
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new BaseException(UserErrorCode.USER_USERNAME_DUPLICATED);
    }
    
    // 이메일 중복 체크
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new BaseException(UserErrorCode.USER_EMAIL_DUPLICATED);
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

  // 로그인
  public String signIn(LoginRequest request) {

    // DB에 있는 유저 정보 가져오기
    User user = userRepository.findByUsername(request.getUsername())
      .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    // request와 DB의 password가 동일한지 체크
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new BaseException(UserErrorCode.INVALID_PASSWORD);
    }

    // 마지막 로그인 시간 저장
    user.setLastLoginDate(LocalDateTime.now());
    userRepository.save(user);

    return jwtTokenProvider.generateToken(user.getUsername(), user.getRole().name());
  }
}
