package com.jelee.librarymanagementsystem.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.user.dto.client.DeleteAccountDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdateEmailReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdateEmailResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdatePasswordReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdatePasswordResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UserInfoResponseDTO;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.service.UserService;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.AuthErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.AuthSuccessCode;
import com.jelee.librarymanagementsystem.global.response.code.UserSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user/me")
@RequiredArgsConstructor
public class UserController {
  
  private final MessageProvider messageProvider;
  private final UserService userService;

  // 사용자 - 사용자 인증 정보
  @GetMapping()
  public ResponseEntity<?> getMyInfo(Authentication authentication) {
    User user = (User) authentication.getPrincipal(); // 인증 객체

    String message = messageProvider.getMessage(AuthSuccessCode.AUTH_USER_VERIFIED.getMessage());

    // user에 인증 정보가 없으면
    if (user == null) {
      message = messageProvider.getMessage(AuthErrorCode.AUTH_UNAUTHORIZED.getMessage());

      return ResponseEntity
                .status(AuthErrorCode.AUTH_UNAUTHORIZED.getHttpStatus())
                .body(ApiResponse.error(
                  AuthErrorCode.AUTH_UNAUTHORIZED, 
                  message, 
                  null));
    }

    // 사용자 - userInfo에 passowrd 제외한 정보 담기
    UserInfoResponseDTO userInfo = new UserInfoResponseDTO(
      user.getUsername(),
      user.getEmail(),
      user.getJoinDate(),
      user.getLastLoginDate()
    );

    return ResponseEntity
              .status(AuthSuccessCode.AUTH_USER_VERIFIED.getHttpStatus())
              .body(ApiResponse.success(
                AuthSuccessCode.AUTH_USER_VERIFIED, 
                message, 
                userInfo));
  }

  // 사용자 - email 업데이트
  @PatchMapping("/email")
  public ResponseEntity<?> updateEmail(
    @RequestBody UpdateEmailReqDTO updateEmail, 
    @AuthenticationPrincipal User user) {
    
    UpdateEmailResDTO responseDTO = userService.updateEmail(user.getUsername(), updateEmail.getEmail());

    String message = messageProvider.getMessage(UserSuccessCode.USER_EMAIL_UPDATE.getMessage());

    return ResponseEntity
              .status(UserSuccessCode.USER_EMAIL_UPDATE.getHttpStatus())
              .body(ApiResponse.success(
                UserSuccessCode.USER_EMAIL_UPDATE, 
                message, 
                responseDTO));
  }

  // 사용자 - password 업데이트
  @PatchMapping("/password")
  public ResponseEntity<?> updatePassword(
    @RequestBody UpdatePasswordReqDTO updatePassword, 
    @AuthenticationPrincipal User user) {
    
    UpdatePasswordResDTO responseDTO = userService.updatePassword(user.getId(), updatePassword);

    String message = messageProvider.getMessage(UserSuccessCode.USER_PASSWORD_UPDATE.getMessage());
    
    return ResponseEntity
              .status(UserSuccessCode.USER_PASSWORD_UPDATE.getHttpStatus())
              .body(ApiResponse.success(
                UserSuccessCode.USER_PASSWORD_UPDATE, 
                message, 
                responseDTO));
  }

  // 사용자 - 회원 탈퇴
  @DeleteMapping()
  public ResponseEntity<?> deleteAccount(@RequestBody DeleteAccountDTO deleteAccount,
                                        @AuthenticationPrincipal User user) {
    userService.deleteAccount(deleteAccount.getPassword(), user.getUsername());

    String message = messageProvider.getMessage(UserSuccessCode.USER_DELETE_ACCOUNT.getMessage());

    return ResponseEntity
              .status(UserSuccessCode.USER_DELETE_ACCOUNT.getHttpStatus())
              .body(ApiResponse.success(
                UserSuccessCode.USER_DELETE_ACCOUNT, 
                message, 
                user.getUsername()));
  }
}
