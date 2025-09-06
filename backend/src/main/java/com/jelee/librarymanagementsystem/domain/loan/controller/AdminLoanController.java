package com.jelee.librarymanagementsystem.domain.loan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanCreateResDTO;
import com.jelee.librarymanagementsystem.domain.loan.service.AdminLoanService;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.LoanSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/loans")
@RequiredArgsConstructor
public class AdminLoanController {

  private final AdminLoanService adminLoanService;
  private final MessageProvider messageProvider;
  
  // 도서 대출 등록
  @PostMapping()
  public ResponseEntity<?> createLoan(@RequestBody AdminLoanCreateReqDTO requestDTO) {

    // 서비스로직
    AdminLoanCreateResDTO responseDTO = adminLoanService.createLoan(requestDTO);

    // 성공메시지
    String message = messageProvider.getMessage(LoanSuccessCode.LOAN_CRATED_SUCCESS.getMessage());

    // 응답
    return ResponseEntity
              .status(LoanSuccessCode.LOAN_CRATED_SUCCESS.getHttpStatus())
              .body(ApiResponse.success(
                LoanSuccessCode.LOAN_CRATED_SUCCESS, 
                message, 
                responseDTO));
  }

  // 전체 대출 목록 조회

  // 대출 상세 조회

  // 대출 타입별 키워드 검색

  // 도서 반납 처리

  // 대출 연장 처리

  // 도서 분실 처리
}
