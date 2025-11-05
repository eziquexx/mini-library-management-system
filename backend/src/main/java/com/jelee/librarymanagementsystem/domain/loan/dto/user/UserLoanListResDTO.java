package com.jelee.librarymanagementsystem.domain.loan.dto.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;

import lombok.Getter;

@Getter
public class UserLoanListResDTO {
  private Long id;
  private Long bookId;
  private String bookTitle;
  private String author;
  private String publisher;
  private LocalDate publishedDate;
  private String location;
  private LocalDateTime loanDate;
  private LocalDateTime dueDate;
  private LocalDateTime returnDate;
  private String borrower;
  private LoanStatus status;
  private boolean extended;
  private boolean reviewWritten;

  public UserLoanListResDTO(Loan loan, boolean reviewWritten) {
    this.id = loan.getId();
    this.bookId = loan.getBook().getId();
    this.bookTitle = loan.getBook().getTitle();
    this.author = loan.getBook().getAuthor();
    this.publisher = loan.getBook().getPublisher();
    this.publishedDate = loan.getBook().getPublishedDate();
    this.location = loan.getBook().getLocation();
    this.loanDate = loan.getLoanDate();
    this.dueDate = loan.getDueDate();
    this.returnDate = loan.getReturnDate();
    this.borrower = loan.getUser().getUsername();
    this.status = loan.getStatus();
    this.extended = loan.isExtended();
    this.reviewWritten = reviewWritten;
  }
}
