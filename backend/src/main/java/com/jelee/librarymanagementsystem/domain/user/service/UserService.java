package com.jelee.librarymanagementsystem.domain.user.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserDeleteResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserListResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserRoleUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserRoleUpdatedResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserSearchResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserStatusUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserStatusUpdateResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.DeleteAccountReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.DeleteAccountResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdateEmailResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdatePasswordReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdatePasswordResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UserInfoResDTO;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.enums.UserSearchType;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.AuthErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  // 사용자 인증 정보
  @Transactional
  public UserInfoResDTO getMyInfo(Authentication authentication) {

    // User 인증 객체
    User user = (User) authentication.getPrincipal();

    // User에 인증 정보 체크
    if (user == null) {
      throw new BaseException(AuthErrorCode.AUTH_UNAUTHORIZED);
    }

    // 반환
    return new UserInfoResDTO(user);
  }

  // email 업데이트
  @Transactional
  public UpdateEmailResDTO updateEmail(String username, String newEmail) {
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

    // user객체에 변경할 이메일, 수정된 날짜 저장 후 DB에 user객체 저장.
    user.setEmail(newEmail);
    user.setUpdatedAt(LocalDateTime.now());
    userRepository.save(user);

    // 응답
    return UpdateEmailResDTO.builder()
              .id(user.getId())
              .username(user.getUsername())
              .email(user.getEmail())
              .updatedAt(user.getUpdatedAt())
              .build();
  }

  // password 업데이트
  @Transactional
  public UpdatePasswordResDTO updatePassword(Long userId, UpdatePasswordReqDTO updatePassword) {

    // 로그인한 사용자 객체
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    // 기존 비밀번호, 새로운 비밀번호 변수에 저장
    String rePassword = updatePassword.getPassword();
    String newPassword = updatePassword.getRepassword();
    
    // 기존 비밀번호와 새로운 비밀번호가 동일한지 체크
    if (passwordEncoder.matches(newPassword, user.getPassword())) {
      throw new BaseException(UserErrorCode.USER_PASSWORD_SAME);
    }

    // 새 비밀번호와 다시 입력 새 비밀번호가 동일한지 체크
    if (!newPassword.equals(rePassword)) {
      throw new BaseException(UserErrorCode.USER_PASSWORD_MISMATCH);
    }

    // 새로운 비밀번호 암호화, 수정된 날짜 저장 후 DB에 user 객체 저장.
    String encodedNewPassword = passwordEncoder.encode(newPassword);
    user.setPassword(encodedNewPassword);
    user.setUpdatedAt(LocalDateTime.now());
    userRepository.save(user);

    return UpdatePasswordResDTO.builder()
              .id(user.getId())
              .username(user.getUsername())
              .updatedAt(user.getUpdatedAt())
              .build();
  }

  // 회원 탈퇴, 삭제
  @Transactional
  public DeleteAccountResDTO deleteAccount(Long userId, DeleteAccountReqDTO deleteAccount) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    String password = deleteAccount.getPassword();

    // 비밀번호가 일치하는지 체크
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new BaseException(UserErrorCode.INVALID_PASSWORD);
    }

    // INACTIVE로 상태 변경 + inactiveAt 시각 저장 후 DB에 user 객체 업데이트 
    user.setStatus(UserStatus.INACTIVE);
    user.setInactiveAt(LocalDateTime.now());
    userRepository.save(user);

    return null;
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
      throw new BaseException(UserErrorCode.USER_SEARCH_TYPE_INVALID);
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

  // 관리자 - 회원 상태 수정
  public UserStatusUpdateResDTO updateUserStatus(Long userId, UserStatusUpdateReqDTO statusUpdateDTO) {

    // 사용자 정보 확인 + 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND, "userId: " + userId));
    
    // 상태 변경 및 저장
    user.setStatus(statusUpdateDTO.getStatus());
    user.setUpdatedAt(LocalDateTime.now());
    userRepository.save(user);

    // 응답 반환
    return UserStatusUpdateResDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .status(user.getStatus())
        .updatedAt(user.getUpdatedAt())
        .build();
  }

  // 관리자 - 회원 삭제
  public UserDeleteResDTO deleteUserAccount(Long userId) {

    // userId로 사용자 조회
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    // 사용자 상태가 DELETED 이면 삭제
    if (user.getStatus() == UserStatus.DELETED) {
      userRepository.delete(user);
    } else {
      throw new BaseException(UserErrorCode.USER_STATUS_NOT_DELETED);
    }
    
    return UserDeleteResDTO.builder()
              .id(user.getId())
              .username(user.getUsername())
              .build();
  }

}
