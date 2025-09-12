package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;

import lombok.Getter;

@Getter
public class AdminLoanLostReqDTO {
  private LoanStatus status;
}
