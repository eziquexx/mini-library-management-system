package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;

import lombok.Getter;

@Getter
public class LoanLostDTO {
  private Long id;
  private LoanStatus loanStatus;
  private LocalDateTime lostDate;

  public LoanLostDTO(Loan loan) {
    this.id = loan.getId();
    this.loanStatus = loan.getStatus();
    this.lostDate = loan.getLostDate();
  }
}
