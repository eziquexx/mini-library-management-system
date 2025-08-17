package com.jelee.librarymanagementsystem.domain.admin.dto;

import java.time.LocalDate;

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
}
