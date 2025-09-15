package com.jelee.librarymanagementsystem.domain.loan.dto.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;

import lombok.Getter;

@Getter
public class UserLoanListResDTO {
  private Long id;
  private String bookTitle;
  private String author;
  private String publisher;
  private LocalDate publishedDate;
  private LocalDateTime loanDate;
  private LocalDateTime returnDate;

  public UserLoanListResDTO(Loan loan) {
    this.id = loan.getId();
    this.bookTitle = loan.getBook().getTitle();
    this.author = loan.getBook().getAuthor();
    this.publisher = loan.getBook().getPublisher();
    this.publishedDate = loan.getBook().getPublishedDate();
    this.loanDate = loan.getLoanDate();
    this.returnDate = loan.getReturnDate();
  }
}
