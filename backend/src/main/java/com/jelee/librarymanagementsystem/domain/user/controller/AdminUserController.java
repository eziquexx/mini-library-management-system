package com.jelee.librarymanagementsystem.domain.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserListResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.AdminUserSearchResDTO;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.enums.UserSearchType;
import com.jelee.librarymanagementsystem.domain.user.service.AdminUserService;
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
    Page<AdminUserListResDTO> responseDTO = adminUserService.allListUsers(page, size, user.getId());

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
      Page<AdminUserSearchResDTO> responseDTO = adminUserService.searchUser(type, keyword, page, size, user.getId());

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

  // 관리자 - 회원 권한 수정
  // @PatchMapping("/{userId}/role")
  // public ResponseEntity<?> updateUserRole(
  //   @PathVariable Long userId,
  //   @RequestBody UserRoleUpdateReqDTO roleUpdateDTO) {
    
  //   UserRoleUpdatedResDTO responseDTO = userService.updateUserRole(userId, roleUpdateDTO);

  //   String message = messageProvider.getMessage(UserSuccessCode.USER_ROLE_UPDATED.getMessage());

  //   return ResponseEntity
  //             .status(UserSuccessCode.USER_ROLE_UPDATED.getHttpStatus())
  //             .body(ApiResponse.success(
  //               UserSuccessCode.USER_ROLE_UPDATED, 
  //               message, 
  //               responseDTO));
  // }

  // 관리자 - 회원 상태 수정
  // @PatchMapping("/{userId}/status")
  // public ResponseEntity<?> updateUserStatus(
  //   @PathVariable Long userId, 
  //   @RequestBody UserStatusUpdateReqDTO statusUpdateDTO) {
    
  //   UserStatusUpdateResDTO responseDTO = userService.updateUserStatus(userId, statusUpdateDTO);
    
  //   String message = messageProvider.getMessage(UserSuccessCode.USER_STATUS_UPDATED.getMessage());

  //   return ResponseEntity
  //             .status(UserSuccessCode.USER_STATUS_UPDATED.getHttpStatus())
  //             .body(ApiResponse.success(
  //               UserSuccessCode.USER_STATUS_UPDATED, 
  //               message, 
  //               responseDTO));
  // }


  // 관리자 - 회원 삭제
  // @DeleteMapping("/{userId}")
  // public ResponseEntity<?> deleteUserAccount(@PathVariable Long userId) {
    
  //   UserDeleteResDTO responseDTO = userService.deleteUserAccount(userId);
    
  //   String message = messageProvider.getMessage(UserSuccessCode.USER_ACCOUNT_DELETED.getMessage());

  //   return ResponseEntity
  //             .status(UserSuccessCode.USER_ACCOUNT_DELETED.getHttpStatus())
  //             .body(ApiResponse.success(
  //               UserSuccessCode.USER_ACCOUNT_DELETED, 
  //               message, 
  //               responseDTO));
  // }

}
