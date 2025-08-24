package com.jelee.librarymanagementsystem.domain.book.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BookUpdateReqDTO {

  @NotBlank
  private String title;

  @NotBlank
  private String isbn;

  @NotBlank
  private String author;

  @NotBlank
  private String publisher;

  @NotNull
  private LocalDate publishedDate;

  @NotBlank
  private String location;

  @NotBlank
  private String description;
}
