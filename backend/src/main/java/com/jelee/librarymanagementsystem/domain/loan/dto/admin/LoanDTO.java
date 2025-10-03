package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;

import lombok.Getter;

@Getter
public class LoanDTO {
  private Long id;
  private LocalDateTime loanDate;
  private LocalDateTime dueDate;

  public LoanDTO(Loan loan) {
    this.id = loan.getId();
    this.loanDate = loan.getLoanDate();
    this.dueDate = loan.getDueDate();
  }
}
