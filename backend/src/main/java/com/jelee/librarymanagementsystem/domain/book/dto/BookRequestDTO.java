package com.jelee.librarymanagementsystem.domain.book.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class BookRequestDTO {
  private String title;
  private String isbn;
  private String author;
  private String publisher;
  private LocalDate publishedDate;
  private String location;
  private String description;
}
