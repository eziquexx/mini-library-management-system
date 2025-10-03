package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;

import lombok.Getter;

@Getter
public class AdminLoanDetailResDTO {
  private LoanSearchDTO loan;
  private BorrowerDTO user;
  private BookSimpleDTO book;

  public AdminLoanDetailResDTO(Loan loan) {
    this.loan = new LoanSearchDTO(loan);
    this.user = new BorrowerDTO(loan.getUser());
    this.book = new BookSimpleDTO(loan.getBook());
  }
}
