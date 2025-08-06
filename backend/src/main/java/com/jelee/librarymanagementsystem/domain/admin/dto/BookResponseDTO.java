package com.jelee.librarymanagementsystem.domain.admin.dto;

import com.jelee.librarymanagementsystem.domain.admin.entity.Book;

import lombok.Getter;

@Getter
public class BookResponseDTO {
  private Long id;
  private String title;

  public BookResponseDTO(Book book) {
    this.id = book.getId();
    this.title = book.getTitle();
  }
}
