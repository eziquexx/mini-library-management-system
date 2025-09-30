package com.jelee.librarymanagementsystem.domain.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserListResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserSearchResDTO;
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

  // 관리자 - 회원 권한 수정
  // public UserRoleUpdatedResDTO updateUserRole(Long userId, UserRoleUpdateReqDTO roleUpdateDTO) {

  //   // 사용자 정보 확인 + 예외 처리
  //   User user = userRepository.findById(userId)
  //       .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND, "userId: " + userId));
    
  //   // 권한 변경 및 저장
  //   user.setRole(roleUpdateDTO.getRole());
  //   user.setUpdatedAt(LocalDateTime.now());
  //   userRepository.save(user);

  //   // 응답 반환
  //   return UserRoleUpdatedResDTO.builder()
  //     .id(user.getId())
  //     .username(user.getUsername())
  //     .role(user.getRole())
  //     .updatedAt(user.getUpdatedAt())
  //     .build();
  // }

  // 관리자 - 회원 상태 수정
  // public UserStatusUpdateResDTO updateUserStatus(Long userId, UserStatusUpdateReqDTO statusUpdateDTO) {

  //   // 사용자 정보 확인 + 예외 처리
  //   User user = userRepository.findById(userId)
  //       .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND, "userId: " + userId));
    
  //   // 상태 변경 및 저장
  //   user.setStatus(statusUpdateDTO.getStatus());
  //   user.setUpdatedAt(LocalDateTime.now());
  //   userRepository.save(user);

  //   // 응답 반환
  //   return UserStatusUpdateResDTO.builder()
  //       .id(user.getId())
  //       .username(user.getUsername())
  //       .status(user.getStatus())
  //       .updatedAt(user.getUpdatedAt())
  //       .build();
  // }

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
