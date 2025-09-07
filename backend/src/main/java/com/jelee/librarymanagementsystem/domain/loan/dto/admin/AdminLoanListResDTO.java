package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;

import lombok.Getter;

@Getter
public class AdminLoanListResDTO {
  private Long id;
  private BorrowerDTO borrower;
  private BookSimpleDTO borrowedBook;
  private LocalDateTime loanDate;
  private LocalDateTime dueDate;
  private LocalDateTime returnDate;
  private boolean extended;
  private LoanStatus status;

  public AdminLoanListResDTO(Loan loan) {
    this.id = loan.getId();
    this.borrower = new BorrowerDTO(loan.getUser());
    this.borrowedBook = new BookSimpleDTO(loan.getBook());
    this.loanDate = loan.getLoanDate();
    this.dueDate = loan.getDueDate();
    this.returnDate = loan.getReturnDate();
    this.extended = loan.isExtended();
    this.status = loan.getStatus();
  }
}
