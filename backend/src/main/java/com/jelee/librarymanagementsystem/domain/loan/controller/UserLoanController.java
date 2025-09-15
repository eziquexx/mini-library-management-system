package com.jelee.librarymanagementsystem.domain.loan.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.loan.dto.user.UserLoanListResDTO;
import com.jelee.librarymanagementsystem.domain.loan.service.UserLoanService;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.LoanSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user/me")
@RequiredArgsConstructor
public class UserLoanController {
  
  private final UserLoanService userLoanService;
  private final MessageProvider messageProvider;

  // 사용자(본인) 도서 대출 내역 조회 (페이징)
  @GetMapping("/loans")
  public ResponseEntity<?> userLoanList(
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size,
    @AuthenticationPrincipal User user
    ) {

    // 서비스로직
    Page<UserLoanListResDTO> responseDTO = userLoanService.userLoanList(page, size, user);

    // 성공메시지
    String message = messageProvider.getMessage(LoanSuccessCode.LOAN_LIST_FETCHED.getMessage());
    
    // 반환
    return ResponseEntity
              .status(LoanSuccessCode.LOAN_LIST_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                LoanSuccessCode.LOAN_LIST_FETCHED, 
                message, 
                responseDTO));
  }

}
