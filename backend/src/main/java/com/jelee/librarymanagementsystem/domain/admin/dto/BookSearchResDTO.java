package com.jelee.librarymanagementsystem.domain.admin.dto;

import java.time.LocalDate;

import com.jelee.librarymanagementsystem.domain.admin.entity.Book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchResDTO {
  private Long id;
  private String title;
  private String author;
  private String publisher;
  private LocalDate publishedDate;
  private String location;

  public BookSearchResDTO(Book book) {
    this.id = book.getId();
    this.title = book.getTitle();
    this.author = book.getAuthor();
    this.publisher = book.getPublisher();
    this.publishedDate = book.getPublishedDate();
    this.location = book.getLocation();
  }
}
