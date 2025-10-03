package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;

import lombok.Getter;

@Getter
public class LoanSearchDTO {
  private Long id;
  private LocalDateTime loanDate;
  private LocalDateTime dueDate;
  private LocalDateTime returnDate;
  private LocalDateTime lostDate;
  private boolean extended;
  private LoanStatus status;

  public LoanSearchDTO(Loan loan) {
    this.id = loan.getId();
    this.loanDate = loan.getLoanDate();
    this.dueDate = loan.getDueDate();
    this.returnDate = loan.getReturnDate();
    this.lostDate = loan.getLostDate();
    this.extended = loan.isExtended();
    this.status = loan.getStatus();
  }
}
