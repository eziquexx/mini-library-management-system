package com.jelee.librarymanagementsystem.domain.book.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;

import lombok.Getter;

@Getter
public class AdminBookUpdateResDTO {
  private Long id;
  private String title;
  private LocalDateTime updatedAt;

  public AdminBookUpdateResDTO(Book book) {
    this.id = book.getId();
    this.title = book.getTitle();
    this.updatedAt = book.getUpdatedAt();
  }
}
