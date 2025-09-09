package com.jelee.librarymanagementsystem.domain.loan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;
import com.jelee.librarymanagementsystem.domain.user.entity.User;

public interface LoanRepository extends JpaRepository<Loan, Long> {
  
  // 사용자의 도서 대출건 조회
  int countByUserAndStatus(User user, LoanStatus status);

  // 도서 대출 기능 - 상태별 조회 후 페이징
  Page<Loan> findByStatus(LoanStatus status, Pageable pageable);

  // 검색 기능: 도서명 + 대출 상태
  Page<Loan> findByBook_TitleContainingIgnoreCaseAndStatus(String bookTitle, LoanStatus status, Pageable pageable);

  // 검색 기능: 도서명만
  Page<Loan> findByBook_TitleContainingIgnoreCase(String bookTitle, Pageable pageable);

  // 검색 기능: 사용자명 + 대출상태
  Page<Loan> findByUser_UsernameContainingIgnoreCaseAndStatus(String username, LoanStatus status, Pageable pageable);

  // 검색 기능: 사용자명만
  Page<Loan> findByUser_UsernameContainingIgnoreCase(String username, Pageable pageable);
}
