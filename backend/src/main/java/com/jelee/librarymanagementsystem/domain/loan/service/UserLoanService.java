package com.jelee.librarymanagementsystem.domain.loan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.loan.dto.user.UserLoanListResDTO;
import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.repository.LoanRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserLoanService {
  
  private final LoanRepository loanRepository;
  private final UserRepository userRepository;

  /*
   * 사용자: 내 도서 대출 내역 (페이징)
   */
  public PageResponse<UserLoanListResDTO> userLoanList(int page, int size, Long userId) {

    // 사용자 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // DB에서 사용자id로 조회
    Page<Loan> result = loanRepository.findByUser_Id(user.getId(), pageable);

    // Page<Loan> -> Page<UserLoanListResDTO>로 맵핑
    Page<UserLoanListResDTO> pageDTO = result.map(UserLoanListResDTO::new);

    // 반환
    return new PageResponse<>(pageDTO);
  }
}
