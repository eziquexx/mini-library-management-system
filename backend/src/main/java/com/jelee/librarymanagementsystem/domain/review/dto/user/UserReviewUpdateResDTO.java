package com.jelee.librarymanagementsystem.domain.review.dto.user;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.review.entity.Review;

import lombok.Getter;

@Getter
public class UserReviewUpdateResDTO {
  private String bookTitle;
  private String content;
  private LocalDateTime updatedDate;

  public UserReviewUpdateResDTO(Review review) {
    this.bookTitle = review.getBook().getTitle();
    this.content = review.getContent();
    this.updatedDate = review.getUpdatedDate();
  }
}
