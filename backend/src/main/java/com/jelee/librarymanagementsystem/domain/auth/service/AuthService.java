package com.jelee.librarymanagementsystem.domain.auth.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.auth.dto.JoinReqDTO;
import com.jelee.librarymanagementsystem.domain.auth.dto.LoginReqDTO;
import com.jelee.librarymanagementsystem.domain.auth.dto.LogoutResDTO;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.jwt.JwtTokenProvider;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  
  // 회원가입
  public Long signUp(JoinReqDTO request) {

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
  public String signIn(LoginReqDTO request) {

    // DB에 있는 유저 정보 가져오기
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

    return jwtTokenProvider.generateToken(user);
  }

  // 로그아웃
  public LogoutResDTO logout(HttpServletRequest request) {
    
    // 토큰을 구한 다음 토큰으로 userId 가져오기
    String accessToken = jwtTokenProvider.resolveTokenFromCookie(request);
    Long userId = jwtTokenProvider.getUserIdFromToken(accessToken);

    // id로 사용자 정보 가져오기
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    return LogoutResDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .build();
  }
}
