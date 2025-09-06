package com.jelee.librarymanagementsystem.domain.loan.service;

import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;
import com.jelee.librarymanagementsystem.domain.book.enums.BookStatus;
import com.jelee.librarymanagementsystem.domain.book.repository.BookRepository;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanCreateResDTO;
import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;
import com.jelee.librarymanagementsystem.domain.loan.repository.LoanRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.BookErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.LoanErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminLoanService {
  
  private final LoanRepository loanRepository;
  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  // 도서 대출 등록
  @Transactional
  public AdminLoanCreateResDTO createLoan(AdminLoanCreateReqDTO requestDTO) {

    // 도서 유효성 검사
    Book book = bookRepository.findById(requestDTO.getBookId())
        .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));

    // 사용자 유효성 검사
    User user = userRepository.findById(requestDTO.getUserId())
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    // 도서 대출 가능 여부 확인
    if (book.getStatus() != BookStatus.AVAILABLE) {
      throw new BaseException(LoanErrorCode.LOAN_ALREADY_BORROWED);
    }

    // 사용자 대출 3건이상은 대출 불가
    int loanedCount = loanRepository.countByUserAndStatus(user, LoanStatus.LOANED);
    if (loanedCount >= 3) {
      throw new BaseException(LoanErrorCode.LOAN_LIMIT_EXCEEDED);
    }
    
    // 대출 생성 & 저장
    Loan loan = Loan.builder()
                  .user(user)
                  .book(book)
                  .build();

    loanRepository.save(loan);

    // 도서 상태 변경
    book.setStatus(BookStatus.BORROWED);

    // 응답
    return new AdminLoanCreateResDTO(loan);
  }
}
