package com.jelee.librarymanagementsystem.domain.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;
import com.jelee.librarymanagementsystem.domain.user.entity.User;

public interface LoanRepository extends JpaRepository<Loan, Long> {
  
  // 사용자의 도서 대출건 조회
  int countByUserAndStatus(User user, LoanStatus status);
}
