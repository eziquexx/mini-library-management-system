package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;

import lombok.Getter;

@Getter
public class AdminLoanCreateResDTO {
  private LoanDTO loan;
  private BorrowerDTO borrower;
  private BookSimpleDTO borrowedBook;

  public AdminLoanCreateResDTO(Loan loan) {
    this.loan = new LoanDTO(loan);
    this.borrower = new BorrowerDTO(loan.getUser());
    this.borrowedBook = new BookSimpleDTO(loan.getBook());
  }
}