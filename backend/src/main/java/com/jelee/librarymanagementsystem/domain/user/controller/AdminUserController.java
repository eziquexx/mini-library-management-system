package com.jelee.librarymanagementsystem.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserDeleteResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserDetailResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserListResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserRoleUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserRoleUpdatedResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserSearchResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserStatusUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserStatusUpdateResDTO;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.enums.UserSearchType;
import com.jelee.librarymanagementsystem.domain.user.service.AdminUserService;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.UserSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
  
  private final AdminUserService adminUserService;
  private final MessageProvider messageProvider;

  /*
   * 관리자: 회원 전체 목록 조회 (페이징)
   */
  @GetMapping()
  public ResponseEntity<?> allListUsers(
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size,
    @AuthenticationPrincipal User user) {
    
    // 서비스로직
    PageResponse<AdminUserListResDTO> responseDTO = adminUserService.allListUsers(page, size, user.getId());

    // 성공 메시지
    String message = messageProvider.getMessage(UserSuccessCode.USER_LIST_FETCHED.getMessage());

    // 응답
    return ResponseEntity
              .status(UserSuccessCode.USER_LIST_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                UserSuccessCode.USER_LIST_FETCHED, 
                message, 
                responseDTO));
  }

  /*
   * 관리자: 회원 상세 조회
   */
  @GetMapping("/{userId}")
  public ResponseEntity<?> detailUser(
    @PathVariable("userId") Long userId, 
    @AuthenticationPrincipal User user) {

      // 서비스로직
      AdminUserDetailResDTO responseDTO = adminUserService.detailUser(userId, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(UserSuccessCode.USER_FETCHED.getMessage());

      // 응답
      return ResponseEntity
                .status(UserSuccessCode.USER_FETCHED.getHttpStatus())
                .body(ApiResponse.success(
                  UserSuccessCode.USER_FETCHED, 
                  message, 
                  responseDTO));
  }

  /*
   * 관리자: 회원 검색 (페이징)
   */
  @GetMapping("search")
  public ResponseEntity<?> searchUser(
    @RequestParam("type") UserSearchType type,
    @RequestParam("keyword") String keyword,
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "10") int size,
    @AuthenticationPrincipal User user) {
      
      // 서비스로직
      PageResponse<AdminUserSearchResDTO> responseDTO = adminUserService.searchUser(type, keyword, page, size, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(UserSuccessCode.USER_FETCHED.getMessage());

      // 응답
      return ResponseEntity
                .status(UserSuccessCode.USER_ACCOUNT_DELETED.getHttpStatus())
                .body(ApiResponse.success(
                  UserSuccessCode.USER_LIST_FETCHED, 
                  message, 
                  responseDTO));    
  }

  /*
   * 관리자: 회원 권한 수정
   */
  @PatchMapping("/{userId}/role")
  public ResponseEntity<?> updateUserRole(
    @PathVariable("userId") Long userId,
    @RequestBody AdminUserRoleUpdateReqDTO requestDTO,
    @AuthenticationPrincipal User user) {
    
      // 서비스로직
      AdminUserRoleUpdatedResDTO responseDTO = adminUserService.updateUserRole(userId, requestDTO, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(UserSuccessCode.USER_ROLE_UPDATED.getMessage());

      // 응답
      return ResponseEntity
                .status(UserSuccessCode.USER_ROLE_UPDATED.getHttpStatus())
                .body(ApiResponse.success(
                  UserSuccessCode.USER_ROLE_UPDATED, 
                  message, 
                  responseDTO));
  }

  /*
   * 관리자: 회원 상태 수정
   */
  @PatchMapping("/{userId}/status")
  public ResponseEntity<?> updateUserStatus(
    @PathVariable("userId") Long userId, 
    @RequestBody AdminUserStatusUpdateReqDTO requestDTO,
    @AuthenticationPrincipal User user) {
    
      // 서비스로직
      AdminUserStatusUpdateResDTO responseDTO = adminUserService.updateUserStatus(userId, requestDTO, user.getId());
      
      // 성공메시지
      String message = messageProvider.getMessage(UserSuccessCode.USER_STATUS_UPDATED.getMessage());

      // 응답
      return ResponseEntity
                .status(UserSuccessCode.USER_STATUS_UPDATED.getHttpStatus())
                .body(ApiResponse.success(
                  UserSuccessCode.USER_STATUS_UPDATED, 
                  message, 
                  responseDTO));
  }

  /*
   * 관리자: 회원 탈퇴 처리
   */
  @PatchMapping("/{userId}/deactivate")
  public ResponseEntity<?> deleteUserAccount(
    @PathVariable("userId") Long userId, 
    @AuthenticationPrincipal User user) {
    
      // 서비스로직
      AdminUserDeleteResDTO responseDTO = adminUserService.deleteUserAccount(userId, user.getId());
      
      // 성공메시지
      String message = messageProvider.getMessage(UserSuccessCode.USER_ACCOUNT_DELETED.getMessage());

      // 응답
      return ResponseEntity
                .status(UserSuccessCode.USER_ACCOUNT_DELETED.getHttpStatus())
                .body(ApiResponse.success(
                  UserSuccessCode.USER_ACCOUNT_DELETED, 
                  message, 
                  responseDTO));
  }

}
