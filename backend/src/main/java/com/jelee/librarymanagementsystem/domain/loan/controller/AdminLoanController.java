package com.jelee.librarymanagementsystem.domain.loan.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanCreateResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanDetailResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanExtendedResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanListResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanLostResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanReturnResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanSearchResDTO;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanSearchType;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;
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
  @GetMapping()
  public ResponseEntity<?> allListLoans(
    @RequestParam(name = "status", required = false) LoanStatus status,
    @RequestParam(name = "page", defaultValue = "0") int page, 
    @RequestParam(name = "size", defaultValue = "10") int size) {
    
    // 서비스로직
    Page<AdminLoanListResDTO> responseDTO = adminLoanService.allListLoans(status, page, size);

    // 성공메시지
    String message = messageProvider.getMessage(LoanSuccessCode.LOAN_LIST_FETCHED.getMessage());
    
    return ResponseEntity
              .status(LoanSuccessCode.LOAN_LIST_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                LoanSuccessCode.LOAN_LIST_FETCHED, 
                message, 
                responseDTO));
  }

  // 대출 상세 조회
  @GetMapping("/{loanId}")
  public ResponseEntity<?> detailLoan(@PathVariable("loanId") Long loanId) {
    
    // 서비스로직
    AdminLoanDetailResDTO responseDTO = adminLoanService.detailLoan(loanId);

    // 성공메시지
    String message = messageProvider.getMessage(LoanSuccessCode.LOAN_DETAIL_FETCHED_SUCCESS.getMessage());

    // 응답
    return ResponseEntity
              .status(LoanSuccessCode.LOAN_DETAIL_FETCHED_SUCCESS.getHttpStatus())
              .body(ApiResponse.success(
                LoanSuccessCode.LOAN_DETAIL_FETCHED_SUCCESS, 
                message, 
                responseDTO));
  }

  // 도서 대출 조건별 검색
  @GetMapping("/search")
  public ResponseEntity<?> searchLoan(
    @RequestParam(name = "type", required = false) LoanSearchType type, 
    @RequestParam(name = "keyword") String keyword, 
    @RequestParam(name = "status", required = false) LoanStatus status,
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size) {

    // 서비스로직
    Page<AdminLoanSearchResDTO> responseDTO = adminLoanService.searchLoan(type, keyword, status, page, size);

    // 성공메시지
    String message = messageProvider.getMessage(LoanSuccessCode.LOAN_FETCHED.getMessage());

    return ResponseEntity
              .status(LoanSuccessCode.LOAN_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                LoanSuccessCode.LOAN_FETCHED, 
                message, 
                responseDTO));
  }

  // 도서 반납 처리
  @PatchMapping("/{loanId}/return")
  public ResponseEntity<?> returnLoan(@PathVariable("loanId") Long loanId) {
    
    // 서비스로직
    AdminLoanReturnResDTO responseDTO = adminLoanService.returnLoan(loanId);

    // 성공메시지
    String message = messageProvider.getMessage(LoanSuccessCode.LOAN_RETURNED_SUCCESS.getMessage());

    return ResponseEntity
              .status(LoanSuccessCode.LOAN_RETURNED_SUCCESS.getHttpStatus())
              .body(ApiResponse.success(
                LoanSuccessCode.LOAN_RETURNED_SUCCESS, 
                message, 
                responseDTO));
  }

  // 도서 대출 연장 처리
  @PatchMapping("/{loanId}/extend")
  public ResponseEntity<?> extendLoan(@PathVariable("loanId") Long loanId) {
    
    // 서비스로직
    AdminLoanExtendedResDTO responseDTO = adminLoanService.extendLoan(loanId);

    // 성공메시지
    String message = messageProvider.getMessage(LoanSuccessCode.LOAN_EXTENDED_SUCCESS.getMessage());

    // 응답
    return ResponseEntity
              .status(LoanSuccessCode.LOAN_EXTENDED_SUCCESS.getHttpStatus())
              .body(ApiResponse.success(
                LoanSuccessCode.LOAN_EXTENDED_SUCCESS, 
                message, 
                responseDTO));
  }

  // 도서 분실 처리
  @PatchMapping("/{loanId}/lost")
  public ResponseEntity<?> loanLostBook(@PathVariable("loanId") Long loanId) {

    // 서비스로직
    AdminLoanLostResDTO responseDTO = adminLoanService.loanLostBook(loanId);

    // 성공메시지
    String message = messageProvider.getMessage(LoanSuccessCode.LOAN_RETURNED_SUCCESS.getMessage());

    // 응답
    return ResponseEntity
              .status(LoanSuccessCode.LOAN_MARKED_LOST_SUCCESS.getHttpStatus())
              .body(ApiResponse.success(
                LoanSuccessCode.LOAN_MARKED_LOST_SUCCESS, 
                message, 
                responseDTO));
  }
}
