package com.jelee.librarymanagementsystem.domain.review.dto.user;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.review.entity.Review;

import lombok.Getter;

@Getter
public class UserReviewDeleteResDTO {
  private Long id;
  private String bookTitle;
  private String username;
  private LocalDateTime deletedAt;

  public UserReviewDeleteResDTO(Review review) {
    this.id = review.getId();
    this.bookTitle = review.getBook().getTitle();
    this.username = review.getUser().getUsername();
    this.deletedAt = LocalDateTime.now();
  }
}
