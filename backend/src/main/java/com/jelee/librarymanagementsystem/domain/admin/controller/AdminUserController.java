package com.jelee.librarymanagementsystem.domain.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserDeleteResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserListResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserRoleUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserRoleUpdatedResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserSearchResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserStatusUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.admin.UserStatusUpdateResDTO;
import com.jelee.librarymanagementsystem.domain.user.service.UserService;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.UserSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
  
  private final UserService userService;
  private final MessageProvider messageProvider;

  // 관리자 - 회원 전체 조회 (+페이징)
  @GetMapping()
  public ResponseEntity<?> allListUsers(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
    
    // Page 기능
    Page<UserListResDTO> ListUsers = userService.allListUsers(page, size);

    // 성공 메시지
    String message = messageProvider.getMessage(UserSuccessCode.USER_LIST_FETCHED.getMessage());

    // 응답
    return ResponseEntity
              .status(UserSuccessCode.USER_LIST_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                UserSuccessCode.USER_LIST_FETCHED, 
                message, 
                ListUsers));
  }

  // 관리자 - 회원 검색
  @GetMapping("search")
  public ResponseEntity<?> searchUser(
    @RequestParam String type,
    @RequestParam String keyword,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {

    // Page 기능 + User 조회
    Page<UserSearchResDTO> user = userService.searchUser(type, keyword, page, size);
    
    // 성공 메시지
    String message = messageProvider.getMessage(UserSuccessCode.USER_SEARCH.getMessage());

    return ResponseEntity
              .status(UserSuccessCode.USER_SEARCH.getHttpStatus())
              .body(ApiResponse.success(
                UserSuccessCode.USER_SEARCH, 
                message, 
                user));
  }

  // 관리자 - 회원 권한 수정
  @PatchMapping("/{userId}/role")
  public ResponseEntity<?> updateUserRole(
    @PathVariable Long userId,
    @RequestBody UserRoleUpdateReqDTO roleUpdateDTO) {
    
    UserRoleUpdatedResDTO responseDTO = userService.updateUserRole(userId, roleUpdateDTO);

    String message = messageProvider.getMessage(UserSuccessCode.USER_ROLE_UPDATE.getMessage());

    return ResponseEntity
              .status(UserSuccessCode.USER_ROLE_UPDATE.getHttpStatus())
              .body(ApiResponse.success(
                UserSuccessCode.USER_ROLE_UPDATE, 
                message, 
                responseDTO));
  }

  // 관리자 - 회원 상태 수정
  @PatchMapping("/{userId}/status")
  public ResponseEntity<?> updateUserStatus(
    @PathVariable Long userId, 
    @RequestBody UserStatusUpdateReqDTO statusUpdateDTO) {
    
    UserStatusUpdateResDTO responseDTO = userService.updateUserStatus(userId, statusUpdateDTO);
    
    String message = messageProvider.getMessage(UserSuccessCode.USER_STATUS_UPDATE.getMessage());

    return ResponseEntity
              .status(UserSuccessCode.USER_STATUS_UPDATE.getHttpStatus())
              .body(ApiResponse.success(
                UserSuccessCode.USER_STATUS_UPDATE, 
                message, 
                responseDTO));
  }


  // 관리자 - 회원 삭제
  @DeleteMapping("/{userId}")
  public ResponseEntity<?> deleteUserAccount(@PathVariable Long userId) {
    
    UserDeleteResDTO responseDTO = userService.deleteUserAccount(userId);
    
    String message = messageProvider.getMessage(UserSuccessCode.USER_DELETE_ACCOUNT.getMessage());

    return ResponseEntity
              .status(UserSuccessCode.USER_DELETE_ACCOUNT.getHttpStatus())
              .body(ApiResponse.success(
                UserSuccessCode.USER_DELETE_ACCOUNT, 
                message, 
                responseDTO));
  }

}
