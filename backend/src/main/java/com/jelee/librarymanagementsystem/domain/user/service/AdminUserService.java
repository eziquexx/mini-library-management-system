package com.jelee.librarymanagementsystem.domain.user.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserListResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserRoleUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserRoleUpdatedResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserSearchResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserStatusUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserStatusUpdateResDTO;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.enums.UserSearchType;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.AuthErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserService {

  private final UserRepository userRepository;

  /*
   * 관리자: 회원 전체 목록 조회 (페이징)
   */
  public Page<AdminUserListResDTO> allListUsers(int page, int size, Long userId) {
    
    // 관리자 권환 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // Page형태로 사용자 조회
    Page<User> result = userRepository.findAll(pageable);

    // User -> AdminUserListResDTO로 맵핑
    Page<AdminUserListResDTO> pageDTO = result.map(AdminUserListResDTO::new);

    // 반환
    return pageDTO;
  }

  /*
   * 관리자: 회원 검색 (페이징)
   */
  public Page<AdminUserSearchResDTO> searchUser(UserSearchType typeStr, String keyword, int page, int size, Long userId) { 
    
    // 관리자 권한 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // 타입별 Page형태로 사용자 검색 조회
    UserSearchType type = typeStr;
    Page<User> result;

    switch(type) {
      case USERNAME:
        result = userRepository.findByUsernameContainingIgnoreCase(keyword, pageable);
        break;
      case EMAIL:
        result = userRepository.findByEmailContainingIgnoreCase(keyword, pageable);
        break;
      default:
        throw new IllegalArgumentException("올바른 타입을 선택해주세요 (USERNAME, EMAIL): " + type);
    }

    // User -> AdminUserSearchResDTO로 맵핑
    Page<AdminUserSearchResDTO> pageDTO = result.map(AdminUserSearchResDTO::new);

    // 반환
    return pageDTO;
  }

  /*
   * 관리자: 회원 권한 수정
   */
  @Transactional
  public AdminUserRoleUpdatedResDTO updateUserRole(Long userId, AdminUserRoleUpdateReqDTO roleUpdateDTO, Long adminUserId) {

    // 관리자 권한 조회 및 예외 처리
    User userAdmin = userRepository.findById(adminUserId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (userAdmin.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 사용자 정보 확인 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND, "userId: " + userId));
    
    // 권한 변경 및 저장
    user.setRole(roleUpdateDTO.getRole());
    user.setUpdatedAt(LocalDateTime.now());
    userRepository.save(user);

    // 반환
    return new AdminUserRoleUpdatedResDTO(user);
  }

  /*
   * 관리자: 회원 상태 수정
   */
  @Transactional
  public AdminUserStatusUpdateResDTO updateUserStatus(Long userId, AdminUserStatusUpdateReqDTO requestDTO, Long adminUserId) {

    // 관리자 권한 조회 및 예외 처리
    User userAdmin = userRepository.findById(adminUserId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (userAdmin.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 사용자 정보 확인 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND, "userId: " + userId));
    
    // 상태 변경 및 저장
    user.setStatus(requestDTO.getStatus());
    user.setUpdatedAt(LocalDateTime.now());
    userRepository.save(user);

    // 반환
    return new AdminUserStatusUpdateResDTO(user);
  }

  // 관리자 - 회원 삭제
  // public UserDeleteResDTO deleteUserAccount(Long userId) {

  //   // userId로 사용자 조회
  //   User user = userRepository.findById(userId)
  //       .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

  //   // 사용자 상태가 DELETED 이면 삭제
  //   if (user.getStatus() == UserStatus.DELETED) {
  //     userRepository.delete(user);
  //   } else {
  //     throw new BaseException(UserErrorCode.USER_STATUS_NOT_DELETED);
  //   }
    
  //   return UserDeleteResDTO.builder()
  //             .id(user.getId())
  //             .username(user.getUsername())
  //             .build();
  // }

}
