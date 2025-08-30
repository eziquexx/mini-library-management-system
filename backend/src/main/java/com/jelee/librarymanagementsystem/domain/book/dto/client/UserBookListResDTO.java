package com.jelee.librarymanagementsystem.domain.book.dto.client;

import java.time.LocalDate;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;

import lombok.Getter;

@Getter
public class UserBookListResDTO {
  private Long id;
  private String title;
  private String author;
  private String publisher;
  private LocalDate publishedDate;
  private String location;

  public UserBookListResDTO(Book book) {
    this.id = book.getId();
    this.title = book.getTitle();
    this.author = book.getAuthor();
    this.publisher = book.getPublisher();
    this.publishedDate = book.getPublishedDate();
    this.location = book.getLocation();
  }
}
