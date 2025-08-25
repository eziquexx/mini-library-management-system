package com.jelee.librarymanagementsystem.domain.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.user.dto.UserListResDTO;
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

  // 관리자 - 회원 권한 수정

  // 관리자 - 회원 삭제

}
