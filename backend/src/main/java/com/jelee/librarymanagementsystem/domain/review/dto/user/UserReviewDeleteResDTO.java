package com.jelee.librarymanagementsystem.domain.review.dto.user;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.review.entity.Review;

import lombok.Getter;

@Getter
public class UserReviewDeleteResDTO {
  private String bookTitle;
  private LocalDateTime deletedAt;

  public UserReviewDeleteResDTO(Review review) {
    this.bookTitle = review.getBook().getTitle();
    this.deletedAt = LocalDateTime.now();
  }
}
