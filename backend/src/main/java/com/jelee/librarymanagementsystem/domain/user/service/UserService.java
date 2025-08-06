package com.jelee.librarymanagementsystem.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.enums.ErrorCode;
import com.jelee.librarymanagementsystem.global.exception.BaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  // email 업데이트
  @Transactional
  public void updateEmail(String username, String newEmail) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
    
    // 동일 이메일인지 체크
    if (user.getEmail().equals(newEmail)) {
      throw new BaseException(ErrorCode.USER_EMAIL_SAME);
    }

    // 이메일 중복 체크
    if (userRepository.existsByEmail(newEmail)) {
      throw new BaseException(ErrorCode.FORBIDDEN);
    }

    // 이메일 저장.authController
    user.setEmail(newEmail);
    userRepository.save(user);
  }

  // password 업데이트
  @Transactional
  public void updatePassword(String username, String newPassword, String rePassword) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
    
    // 기존 비밀번호와 새로운 비밀번호가 동일한지 체크
    if (passwordEncoder.matches(newPassword, user.getPassword())) {
      throw new BaseException(ErrorCode.USER_PASSWORD_SAME);
    }

    // 새 비밀번호와 다시 입력 새 비밀번호가 동일한지 체크
    if (!newPassword.equals(rePassword)) {
      throw new BaseException(ErrorCode.USER_PASSWORD_NOTSAME);
    }

    // 새로운 비밀번호 암호화 후 저장.
    String encodedNewPassword = passwordEncoder.encode(newPassword);
    user.setPassword(encodedNewPassword);
    userRepository.save(user);
  }

  @Transactional
  public void deleteAccount(String password, String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
    
    // 비밀번호가 일치하는지 체크
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new BaseException(ErrorCode.INVALID_PASSWORD);
    }

    userRepository.delete(user);
  }
}
