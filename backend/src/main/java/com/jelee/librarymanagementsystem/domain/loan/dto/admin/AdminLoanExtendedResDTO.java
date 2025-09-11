package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;

import lombok.Getter;

@Getter
public class AdminLoanExtendedResDTO {
  private Long id;
  private String username;
  private String bookTitle;
  private LocalDateTime loanDate;
  private LocalDateTime dueDate;
  private boolean extended;
  private LoanStatus status;

  public AdminLoanExtendedResDTO(Loan loan) {
    this.id = loan.getId();
    this.username = loan.getUser().getUsername();
    this.bookTitle = loan.getBook().getTitle();
    this.loanDate = loan.getLoanDate();
    this.dueDate = loan.getDueDate();
    this.extended = loan.isExtended();
    this.status = loan.getStatus();
  }
}
