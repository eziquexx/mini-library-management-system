package com.jelee.librarymanagementsystem.domain.book.dto;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;

import lombok.Getter;

@Getter
public class BookResponseDTO {
  private Long id;
  private String isbn;
  private String title;

  public BookResponseDTO(Book book) {
    this.id = book.getId();
    this.isbn = book.getIsbn();
    this.title = book.getTitle();
  }
}
