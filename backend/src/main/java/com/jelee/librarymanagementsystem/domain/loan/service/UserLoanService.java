package com.jelee.librarymanagementsystem.domain.loan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jelee.librarymanagementsystem.domain.loan.dto.user.UserLoanExtendedResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.user.UserLoanListResDTO;
import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;
import com.jelee.librarymanagementsystem.domain.loan.repository.LoanRepository;
import com.jelee.librarymanagementsystem.domain.review.entity.Review;
import com.jelee.librarymanagementsystem.domain.review.repository.ReviewRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.LoanErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserLoanService {
  
  private final LoanRepository loanRepository;
  private final UserRepository userRepository;
  private final ReviewRepository reviewRepository;

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
    // Page<Loan> result = loanRepository.findByUser_Id(user.getId(), pageable);
    Page<Loan> loans = loanRepository.findByUser_IdOrderByLoanDateDesc(user.getId(), pageable);

    // Page<Loan> -> Page<UserLoanListResDTO>로 맵핑
    // 리뷰 작성 여부 체크 추가
    Page<UserLoanListResDTO> pageDTO = loans.map(loan -> {
      boolean reviewWritten = reviewRepository.existsByBook_IdAndUser_Id(loan.getBook().getId(), loan.getUser().getId());
      Review review = reviewRepository.findByBook_IdAndUser_Id(loan.getBook().getId(), loan.getUser().getId());

      Long reviewId = null;
      if (review != null) {
        reviewId = review.getId();
      }
      return new UserLoanListResDTO(loan, reviewId, reviewWritten);
    });

    // 반환
    return new PageResponse<>(pageDTO);
  }

  /*
   * 사용자: 도서 대출 연장
   */
  @Transactional
  public UserLoanExtendedResDTO extendLoan(Long loanId, Long userId) {

    // 사용자 조회 및 예외 처리
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    // loanId로 조회 및 예외처리
    Loan loan = loanRepository.findById(loanId)
        .orElseThrow(() -> new BaseException(LoanErrorCode.LOAN_NOT_FOUND));
    
    // 로그인 사용자와 대출자와 동일한지 확인
    if (user.getId() != loan.getUser().getId()) {
      throw new BaseException(LoanErrorCode.LOAN_USER_NOT_SAME);
    }

    // loan 상태가 대출중인 것만 가능
    // 연체, 반납, 분실 상태는 불가
    if (loan.getStatus() != LoanStatus.LOANED) {
      throw new BaseException(LoanErrorCode.LOAN_CANNOT_EXTEND);
    }

    // loan 대출 연장 여부 체크 및 예외처리
    if (loan.isExtended() != false) {
      throw new BaseException(LoanErrorCode.LOAN_ALREADY_EXTENDED);
    }

    // 대출 연장 및 반납 기간 업데이트
    // 대출 연장은 1회 가능, 7일 추가
    loan.setExtended(true);
    loan.setDueDate(loan.getDueDate().plusDays(7));

    // 반환
    return new UserLoanExtendedResDTO(loan);
  }

  /*
   * 사용자: 도서 대출 검색
   */
  public PageResponse<UserLoanListResDTO> searchLoan(String keyword, int page, int size, Long userId) {
    
    // 사용자 조회 및 예외 처리
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // 검색 조회
    Page<Loan> loans = loanRepository.findByUser_IdAndBook_TitleContainingIgnoreCaseOrBook_AuthorContainingIgnoreCaseOrderByLoanDateDesc(user.getId(), keyword, keyword, pageable);

    // Page<Loan> -> Page<UserLoanListResDTO>로 맵핑
    // 리뷰 작성 여부 체크 추가
    Page<UserLoanListResDTO> pageDTO = loans.map(loan -> {
      boolean reviewWritten = reviewRepository.existsByBook_IdAndUser_Id(loan.getBook().getId(), loan.getUser().getId());
      Review review = reviewRepository.findByBook_IdAndUser_Id(loan.getBook().getId(), loan.getUser().getId());

      Long reviewId = null;
      if (review != null) {
        reviewId = review.getId();
      }
      return new UserLoanListResDTO(loan, reviewId, reviewWritten);
    });

    // 반환
    return new PageResponse<>(pageDTO);
  }
}
