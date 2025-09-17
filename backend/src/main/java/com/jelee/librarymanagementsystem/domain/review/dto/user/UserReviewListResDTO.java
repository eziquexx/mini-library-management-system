package com.jelee.librarymanagementsystem.domain.review.dto.user;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.review.entity.Review;

import lombok.Getter;

@Getter
public class UserReviewListResDTO {
  private String bookTitle;
  private String content;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  public UserReviewListResDTO(Review review) {
    this.bookTitle = review.getBook().getTitle();
    this.content = review.getContent();
    this.createdDate = review.getCreatedDate();
    this.updatedDate = review.getUpdatedDate();
  }
}
