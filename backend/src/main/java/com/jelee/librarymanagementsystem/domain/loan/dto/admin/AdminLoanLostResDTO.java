package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.book.enums.BookStatus;
import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;

import lombok.Getter;

@Getter
public class AdminLoanLostResDTO {
  private Long id;
  private LoanStatus loanStatus;
  private LocalDateTime lostDate;

  private Long bookId;
  private String bookTitle;
  private BookStatus bookStatus;
  private LocalDateTime lostedAt;
  
  private Long userId;
  private String username;

  public AdminLoanLostResDTO(Loan loan) {
    this.id = loan.getId();
    this.loanStatus = loan.getStatus();
    this.lostDate = loan.getLostDate();
    
    this.bookId = loan.getBook().getId();
    this.bookTitle = loan.getBook().getTitle();
    this.bookStatus = loan.getBook().getStatus();
    this.lostedAt = loan.getBook().getLostedAt();
    
    this.userId = loan.getUser().getId();
    this.username = loan.getUser().getUsername();
  }
}
