package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;

import lombok.Getter;

@Getter
public class AdminLoanListResDTO {
  private LoanSearchDTO loan;
  private BorrowerDTO borrower;
  private BookSimpleDTO borrowedBook;

  public AdminLoanListResDTO(Loan loan) {
    this.loan = new LoanSearchDTO(loan);
    this.borrower = new BorrowerDTO(loan.getUser());
    this.borrowedBook = new BookSimpleDTO(loan.getBook());
  }
}
