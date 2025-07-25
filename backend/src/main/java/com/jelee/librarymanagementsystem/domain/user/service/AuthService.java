package com.jelee.librarymanagementsystem.domain.user.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.user.dto.JoinRequest;
import com.jelee.librarymanagementsystem.domain.user.dto.LoginRequest;
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

  // 로그인
  public Long signIn(LoginRequest request) {

    // DB에 있는 비밀번호 가져오기
    User user = userRepository.findByUsername(request.getUsername())
      .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
    
    // request와 DB의 username과 password가 동일한지 체크.
    if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      return user.getId();
    } else {
      throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
    }
  }
}
