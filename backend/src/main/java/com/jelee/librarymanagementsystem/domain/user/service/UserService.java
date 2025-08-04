package com.jelee.librarymanagementsystem.domain.user.service;

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
}
