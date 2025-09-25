package com.jelee.librarymanagementsystem.domain.auth.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.auth.dto.JoinReqDTO;
import com.jelee.librarymanagementsystem.domain.auth.dto.JoinResDTO;
import com.jelee.librarymanagementsystem.domain.auth.dto.LoginReqDTO;
import com.jelee.librarymanagementsystem.domain.auth.dto.LoginResDTO;
import com.jelee.librarymanagementsystem.domain.auth.dto.LogoutResDTO;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.jwt.JwtTokenProvider;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  
  /*
   * 공용: 회원가입
   */
  @Transactional
  public JoinResDTO signUp(JoinReqDTO request) {

    // 아이디 중복 체크
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new BaseException(UserErrorCode.USER_USERNAME_DUPLICATED);
    }
    
    // 이메일 중복 체크
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new BaseException(UserErrorCode.USER_EMAIL_DUPLICATED);
    }

    // User 엔티티 생성
    User user = User.builder()
              .username(request.getUsername())
              .password(passwordEncoder.encode(request.getPassword()))
              .email(request.getEmail())
              .role(Role.ROLE_USER)
              .joinDate(LocalDateTime.now())
              .build();
    
    // user 저장
    userRepository.save(user);
    
    // 반환
    return new JoinResDTO(user);
  }

  /*
   * 공용: 로그인
   */
  @Transactional
  public LoginResDTO signIn(LoginReqDTO request) {

    // 사용자 유효 검사
    User user = userRepository.findByUsername(request.getUsername())
      .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    // request와 DB의 password가 동일한지 체크
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new BaseException(UserErrorCode.INVALID_PASSWORD);
    }

    // 사용자의 상태가 ACTIVE인지 체크
    if (user.getStatus() == UserStatus.INACTIVE) {
      throw new BaseException(UserErrorCode.USER_STATUS_INACTIVE);
    } else if (user.getStatus() == UserStatus.SUSPENDED) {
      throw new BaseException(UserErrorCode.USER_STATUS_SUSPENDED);
    } else if (user.getStatus() == UserStatus.DELETED) {
      throw new BaseException(UserErrorCode.USER_STATUS_DELETED);
    }

    // 마지막 로그인 시간 저장
    user.setLastLoginDate(LocalDateTime.now());
    userRepository.save(user);

    // 토큰 생성
    String token = jwtTokenProvider.generateToken(user);

    // 반환
    return new LoginResDTO(user, token);
  }

  /*
   * 공용: 로그아웃
   */
  public LogoutResDTO logout(Long userId) {
    
    // userId로 사용자 정보 조회
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    // 반환    
    return new LogoutResDTO(user);
  }
}
