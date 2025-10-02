package com.jelee.librarymanagementsystem.domain.book.dto.admin;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;

import lombok.Getter;

@Getter
public class AdminBookDeleteResDTO {
  private Long id;
  private String title;

  public AdminBookDeleteResDTO(Book book) {
    this.id = book.getId();
    this.title = book.getTitle();
  }
}
