package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;
import com.jelee.librarymanagementsystem.domain.book.enums.BookStatus;

import lombok.Getter;

@Getter
public class BookLostDTO {
  private Long id;
  private String title;
  private BookStatus status;
  private LocalDateTime lostedAt;

  public BookLostDTO(Book book) {
    this.id = book.getId();
    this.title = book.getTitle();
    this.status = book.getStatus();
    this.lostedAt = book.getLostedAt();
  }
}
