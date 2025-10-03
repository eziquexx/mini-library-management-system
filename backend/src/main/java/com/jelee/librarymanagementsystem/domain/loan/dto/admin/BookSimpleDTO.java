package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;
import com.jelee.librarymanagementsystem.domain.book.enums.BookStatus;

import lombok.Getter;

@Getter
public class BookSimpleDTO {
  private Long id;
  private String title;
  private BookStatus status;

  public BookSimpleDTO(Book book) {
    this.id = book.getId();
    this.title = book.getTitle();
    this.status = book.getStatus();
  }
}
