package com.jelee.librarymanagementsystem.domain.user.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserListResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserRoleUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserRoleUpdatedResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserSearchResDTO;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.enums.UserSearchType;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

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
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    // 동일 이메일인지 체크
    if (user.getEmail().equals(newEmail)) {
      throw new BaseException(UserErrorCode.USER_EMAIL_SAME);
    }

    // 이메일 중복 체크
    if (userRepository.existsByEmail(newEmail)) {
      throw new BaseException(UserErrorCode.USER_EMAIL_DUPLICATED);
    }

    // 이메일 저장.authController
    user.setEmail(newEmail);
    userRepository.save(user);
  }

  // password 업데이트
  @Transactional
  public void updatePassword(String username, String newPassword, String rePassword) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    // 기존 비밀번호와 새로운 비밀번호가 동일한지 체크
    if (passwordEncoder.matches(newPassword, user.getPassword())) {
      throw new BaseException(UserErrorCode.USER_PASSWORD_SAME);
    }

    // 새 비밀번호와 다시 입력 새 비밀번호가 동일한지 체크
    if (!newPassword.equals(rePassword)) {
      throw new BaseException(UserErrorCode.USER_PASSWORD_NOTSAME);
    }

    // 새로운 비밀번호 암호화 후 저장.
    String encodedNewPassword = passwordEncoder.encode(newPassword);
    user.setPassword(encodedNewPassword);
    userRepository.save(user);
  }

  // 회원 탈퇴, 삭제
  @Transactional
  public void deleteAccount(String password, String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    // 비밀번호가 일치하는지 체크
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new BaseException(UserErrorCode.INVALID_PASSWORD);
    }

    userRepository.delete(user);
  }


  /*
   * 관리자 - 회원 관리
   */

  // 관리자 - 회원 전체 조회 (+페이징)
  public Page<UserListResDTO> allListUsers(int page, int size) {

    // Pageable 기능
    Pageable pageable = PageRequest.of(page, size);

    // User 전체 조회 가져와서 Page로 변경
    Page<User> result = userRepository.findAll(pageable);

    // Page -> List로 변경
    List<UserListResDTO> dtoList = result.getContent()
        .stream()
        .map(UserListResDTO::new)
        .toList();

    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  }

  // 관리자 - 회원 검색 (+페이징)
  public Page<UserSearchResDTO> searchUser(String typeStr, String keyword, int page, int size) {

    // Pageable 기능
    Pageable pageable = PageRequest.of(page, size);

    // 타입 예외처리
    UserSearchType type;
    try {
      type = UserSearchType.valueOf(typeStr.toUpperCase());
    } catch(IllegalArgumentException e) {
      throw new BaseException(UserErrorCode.USER_SEARCH_TYPE_FAILED);
    }

    // 검색 결과 Page<User> 타입으로 저장
    Page<User> result;

    // Switch문 - type별 조건 실행
    switch (type) {
      case USERNAME:
        result = userRepository.findByUsernameContainingIgnoreCase(keyword, pageable);
        break;
      case EMAIL:
        result = userRepository.findByEmailContainingIgnoreCase(keyword, pageable);
        break;
      default:
        throw new IllegalArgumentException("Unexpected search type: " + type);
    }

    // 검색 결과 예외 처리
    if (result.isEmpty()) {
      throw new BaseException(UserErrorCode.USER_NOT_FOUND);
    }

    // Page -> List 형변환
    List<UserSearchResDTO> dtoList = result.getContent()
        .stream()
        .map(UserSearchResDTO::new)
        .toList();

    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  }

  // 관리자 - 회원 권한 수정
  public UserRoleUpdatedResDTO updateUserRole(Long userId, UserRoleUpdateReqDTO roleUpdateDTO) {

    // 사용자 정보 확인 + 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND, "userId: " + userId));
    
    // 권한 변경 및 저장
    user.setRole(roleUpdateDTO.getRole());
    user.setUpdatedAt(LocalDateTime.now());
    userRepository.save(user);

    // 응답 반환
    return UserRoleUpdatedResDTO.builder()
      .id(user.getId())
      .username(user.getUsername())
      .role(user.getRole())
      .updatedAt(user.getUpdatedAt())
      .build();
  }

  // 관리자 - 회원 삭제

}
