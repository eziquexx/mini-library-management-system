package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;

import lombok.Getter;

@Getter
public class AdminLoanSearchResDTO {
  private Long id;
  private BorrowerDTO user;
  private BookSimpleDTO book;
  private LocalDateTime loanDate;
  private LocalDateTime dueDate;
  private LocalDateTime returnDate;
  private boolean extended;
  private LoanStatus status;

  public AdminLoanSearchResDTO(Loan loan) {
    this.id = loan.getId();
    this.user = new BorrowerDTO(loan.getUser());
    this.book = new BookSimpleDTO(loan.getBook());
    this.loanDate = loan.getLoanDate();
    this.dueDate = loan.getDueDate();
    this.returnDate = loan.getReturnDate();
    this.extended = loan.isExtended();
    this.status = loan.getStatus();
  }
}
