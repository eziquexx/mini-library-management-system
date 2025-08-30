package com.jelee.librarymanagementsystem.domain.book.dto.admin;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;

import lombok.Getter;

@Getter
public class AdminBookCreateResDTO {
  private Long id;
  private String isbn;
  private String title;

  public AdminBookCreateResDTO(Book book) {
    this.id = book.getId();
    this.isbn = book.getIsbn();
    this.title = book.getTitle();
  }
}
