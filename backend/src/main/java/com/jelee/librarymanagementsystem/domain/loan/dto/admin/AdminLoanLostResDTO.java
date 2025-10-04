package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;

import lombok.Getter;

@Getter
public class AdminLoanLostResDTO {
  private LoanLostDTO loan;
  private BookLostDTO book;
  private BorrowerDTO user;

  public AdminLoanLostResDTO(Loan loan) {
    this.loan = new LoanLostDTO(loan);
    this.book = new BookLostDTO(loan.getBook());
    this.user = new BorrowerDTO(loan.getUser());
  }
}
