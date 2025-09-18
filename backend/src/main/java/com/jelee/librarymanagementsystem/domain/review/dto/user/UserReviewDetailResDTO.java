package com.jelee.librarymanagementsystem.domain.review.dto.user;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.review.entity.Review;

import lombok.Getter;

@Getter
public class UserReviewDetailResDTO {
  private String bookTitle;
  private String author;
  private String publisher;
  private String content;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  public UserReviewDetailResDTO(Review review) {
    this.bookTitle = review.getBook().getTitle();
    this.author = review.getBook().getAuthor();
    this.publisher = review.getBook().getPublisher();
    this.content = review.getContent();
    this.createdDate = review.getCreatedDate();
    this.updatedDate = review.getUpdatedDate();
  }
}
