package com.jelee.librarymanagementsystem.domain.user.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jelee.librarymanagementsystem.domain.user.dto.client.DeleteAccountReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.DeleteAccountResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdateEmailReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdateEmailResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdatePasswordReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdatePasswordResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UserInfoResDTO;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /*
   * 사용자: 본인 인증 정보
   */
  @Transactional(readOnly = true)
  public UserInfoResDTO getMyInfo(Long userId) {

    // User 조회 및 객체 생성, 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    // 반환
    return new UserInfoResDTO(user);
  }

  /*
   * 사용자: 이메일 변경
   */
  @Transactional
  public UpdateEmailResDTO updateEmail(Long userId, UpdateEmailReqDTO requestDTO) {

    // 사용자 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    // 기존과 변경 이메일이 동일한지 체크
    String newEmail = requestDTO.getEmail();
    if (user.getEmail().equals(newEmail)) {
      throw new BaseException(UserErrorCode.USER_EMAIL_SAME);
    }

    // 이메일 중복 체크
    if (userRepository.existsByEmail(newEmail)) {
      throw new BaseException(UserErrorCode.USER_EMAIL_DUPLICATED);
    }

    // user객체에 변경할 이메일, 수정된 날짜 저장 후 DB에 user객체 저장.
    user.setEmail(newEmail);
    user.setUpdatedAt(LocalDateTime.now());
    userRepository.save(user);

    // 반환
    return new UpdateEmailResDTO(user);
  }

  /*
   * 사용자: 비밀번호 변경
   */
  @Transactional
  public UpdatePasswordResDTO updatePassword(Long userId, UpdatePasswordReqDTO requestDTO) {

    // 사용자 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    // 새로운 비밀번호, 확인용 새로운 비밀번호를 변수에 저장
    String newPassword = requestDTO.getPassword();
    String rePassword = requestDTO.getRepassword();
    
    // 기존 비밀번호와 새로운 비밀번호가 동일한지 체크
    if (passwordEncoder.matches(newPassword, user.getPassword())) {
      throw new BaseException(UserErrorCode.USER_PASSWORD_SAME);
    }

    // 새 비밀번호와 확인용 새 비밀번호가 동일한지 체크
    if (!newPassword.equals(rePassword)) {
      throw new BaseException(UserErrorCode.USER_PASSWORD_MISMATCH);
    }

    // 새로운 비밀번호 암호화, 수정된 날짜 저장 후 DB에 user 객체 저장.
    String encodedNewPassword = passwordEncoder.encode(newPassword);
    user.setPassword(encodedNewPassword);
    user.setUpdatedAt(LocalDateTime.now());
    userRepository.save(user);

    // 반환
    return new UpdatePasswordResDTO(user);
  }

  /*
   * 사용자: 회원 탈퇴
   * INACTIVE 상태 변경(비활성화), 30일 후 자동으로 DELETED 상태 변경(UserStatusScheduler)
   */
  @Transactional
  public DeleteAccountResDTO deleteAccount(Long userId, DeleteAccountReqDTO requestDTO) {

    // 사용자 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
        
    // 비밀번호가 일치하는지 체크
    String password = requestDTO.getPassword();
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new BaseException(UserErrorCode.INVALID_PASSWORD);
    }

    // INACTIVE로 상태 변경 + inactiveAt 시각 저장 후 DB에 user 객체 업데이트 
    user.setStatus(UserStatus.INACTIVE);
    user.setInactiveAt(LocalDateTime.now());
    userRepository.save(user);

    // 반환
    return new DeleteAccountResDTO(user);
  }

}
