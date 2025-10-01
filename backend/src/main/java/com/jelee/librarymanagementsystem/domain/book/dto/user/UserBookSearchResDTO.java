package com.jelee.librarymanagementsystem.domain.book.dto.user;

import java.time.LocalDate;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;
import com.jelee.librarymanagementsystem.domain.book.enums.BookStatus;

import lombok.Getter;

@Getter
public class UserBookSearchResDTO {
  private Long id;
  private String title;
  private String isbn;
  private String author;
  private String publisher;
  private LocalDate publishedDate;
  private BookStatus status;
  private String location;

  public UserBookSearchResDTO(Book book) {
    this.id = book.getId();
    this.title = book.getTitle();
    this.isbn = book.getIsbn();
    this.author = book.getAuthor();
    this.publisher = book.getPublisher();
    this.publishedDate = book.getPublishedDate();
    this.status = book.getStatus();
    this.location = book.getLocation();
  }
}
