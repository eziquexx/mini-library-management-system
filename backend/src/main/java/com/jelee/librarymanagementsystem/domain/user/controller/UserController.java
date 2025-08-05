package com.jelee.librarymanagementsystem.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.user.dto.UpdateEmailDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.UpdatePasswordDTO;
import com.jelee.librarymanagementsystem.domain.user.dto.UserInfoResponseDTO;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.service.UserService;
import com.jelee.librarymanagementsystem.global.enums.ErrorCode;
import com.jelee.librarymanagementsystem.global.enums.SuccessCode;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user/me")
@RequiredArgsConstructor
public class UserController {
  
  private final MessageProvider messageProvider;
  private final UserService userService;

  // 사용자 인증 정보
  @GetMapping()
  public ResponseEntity<?> getMyInfo(Authentication authentication) {
    User user = (User) authentication.getPrincipal(); // 인증 객체

    String message = messageProvider.getMessage(SuccessCode.USER_AUTHORIZED_SUCCESS.getMessage());

    // user에 인증 정보가 없으면
    if (user == null) {
      message = messageProvider.getMessage(ErrorCode.UNAUTHORIZED.getMessage());

      return ResponseEntity
                .status(ErrorCode.UNAUTHORIZED.getHttpStatus())
                .body(ApiResponse.error(
                  ErrorCode.UNAUTHORIZED, 
                  message, 
                  null));
    }

    // userInfo에 passowrd 제외한 정보 담기
    UserInfoResponseDTO userInfo = new UserInfoResponseDTO(
      user.getUsername(),
      user.getEmail(),
      user.getJoinDate(),
      user.getLastLoginDate()
    );

    return ResponseEntity
              .status(SuccessCode.USER_AUTHORIZED_SUCCESS.getHttpStatus())
              .body(ApiResponse.success(
                SuccessCode.USER_AUTHORIZED_SUCCESS, 
                message, 
                userInfo));
  }

  // email 업데이트
  @PatchMapping("/email")
  public ResponseEntity<?> updateEmail(@RequestBody UpdateEmailDTO updateEmail,
                                      @AuthenticationPrincipal User user) {
    userService.updateEmail(user.getUsername(), updateEmail.getEmail());
    String message = messageProvider.getMessage(SuccessCode.USER_EMAIL_UPDATE_SUCCESS.getMessage());
    return ResponseEntity
              .status(SuccessCode.USER_EMAIL_UPDATE_SUCCESS.getHttpStatus())
              .body(ApiResponse.success(
                SuccessCode.USER_EMAIL_UPDATE_SUCCESS, 
                message, 
                user.getEmail()));
  }

  // password 업데이트
  @PatchMapping("/password")
  public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO updatePassword,
                                          @AuthenticationPrincipal User user) {
    userService.updatePassword(user.getUsername(), updatePassword.getPassword(), updatePassword.getRepassword());

    String message = messageProvider.getMessage(SuccessCode.USER_PASSWORD_UPDATE_SUCCESS.getMessage());
    
    return ResponseEntity
              .status(SuccessCode.USER_PASSWORD_UPDATE_SUCCESS.getHttpStatus())
              .body(ApiResponse.success(
                SuccessCode.USER_PASSWORD_UPDATE_SUCCESS, 
                message, 
                user.getUsername()));
  }
}
