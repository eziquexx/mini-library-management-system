package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;

import lombok.Getter;

@Getter
public class AdminLoanReturnResDTO {
  private Long id;
  private String username;
  private String bookTitle;
  private LocalDateTime returnDate;
  private LoanStatus status;

  public AdminLoanReturnResDTO(Loan loan) {
    this.id = loan.getId();
    this.username = loan.getUser().getUsername();
    this.bookTitle = loan.getBook().getTitle();
    this.returnDate = loan.getReturnDate();
    this.status = loan.getStatus();
  }
}
