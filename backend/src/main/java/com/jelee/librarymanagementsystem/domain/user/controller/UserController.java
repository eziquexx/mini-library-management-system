package com.jelee.librarymanagementsystem.domain.user.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.user.dto.client.DeleteAccountReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.DeleteAccountResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdateEmailReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdateEmailResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdatePasswordReqDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UpdatePasswordResDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.client.UserInfoResDTO;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.service.UserService;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.AuthSuccessCode;
import com.jelee.librarymanagementsystem.global.response.code.UserSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user/me")
@RequiredArgsConstructor
public class UserController {
  
  private final MessageProvider messageProvider;
  private final UserService userService;

  /*
   * 사용자: 본인 인증 정보
   */
  @GetMapping()
  public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal User user) {

    // 서비스 로직
    UserInfoResDTO responseDTO = userService.getMyInfo(user.getId());

    // 성공메시지
    String message = messageProvider.getMessage(AuthSuccessCode.AUTH_USER_VERIFIED.getMessage());

    // 반환
    return ResponseEntity
              .status(AuthSuccessCode.AUTH_USER_VERIFIED.getHttpStatus())
              .body(ApiResponse.success(
                AuthSuccessCode.AUTH_USER_VERIFIED, 
                message, 
                responseDTO));
  }

  /*
   * 사용자: 이메일 변경
   */
  @PatchMapping("/email")
  public ResponseEntity<?> updateEmail(
    @RequestBody UpdateEmailReqDTO requestDTO, 
    @AuthenticationPrincipal User user) {
    
      // 서비스로직
      UpdateEmailResDTO responseDTO = userService.updateEmail(user.getId(), requestDTO);

      // 성공메시지
      String message = messageProvider.getMessage(UserSuccessCode.USER_EMAIL_UPDATE.getMessage());

      // 응답
      return ResponseEntity
                .status(UserSuccessCode.USER_EMAIL_UPDATE.getHttpStatus())
                .body(ApiResponse.success(
                  UserSuccessCode.USER_EMAIL_UPDATE, 
                  message, 
                  responseDTO));
  }

  /*
   * 사용자: 비밀번호 변경
   */
  @PatchMapping("/password")
  public ResponseEntity<?> updatePassword(
    @RequestBody UpdatePasswordReqDTO requestDTO, 
    @AuthenticationPrincipal User user) {
    
      // 서비스로직
      UpdatePasswordResDTO responseDTO = userService.updatePassword(user.getId(), requestDTO);

      // 성공메시지
      String message = messageProvider.getMessage(UserSuccessCode.USER_PASSWORD_UPDATE.getMessage());
    
    return ResponseEntity
              .status(UserSuccessCode.USER_PASSWORD_UPDATE.getHttpStatus())
              .body(ApiResponse.success(
                UserSuccessCode.USER_PASSWORD_UPDATE, 
                message, 
                responseDTO));
  }

  // 사용자 - 회원 탈퇴
  @PostMapping("/withdraw")
  public ResponseEntity<?> deleteAccount(
    @RequestBody DeleteAccountReqDTO deleteAccount,
    @AuthenticationPrincipal User user,
    HttpServletResponse response) {
    
    DeleteAccountResDTO responseDTO = userService.deleteAccount(user.getId(), deleteAccount);

    ResponseCookie deleteCookie = ResponseCookie.from("JWT", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
              
    response.addHeader("Set-Cookie", deleteCookie.toString());

    String message = messageProvider.getMessage(UserSuccessCode.USER_ACCOUNT_DELETED.getMessage());

    return ResponseEntity
              .status(UserSuccessCode.USER_ACCOUNT_DELETED.getHttpStatus())
              .body(ApiResponse.success(
                UserSuccessCode.USER_ACCOUNT_DELETED, 
                message, 
                responseDTO));
  }
}
