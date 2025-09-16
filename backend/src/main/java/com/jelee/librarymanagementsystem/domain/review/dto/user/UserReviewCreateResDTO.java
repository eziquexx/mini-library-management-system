package com.jelee.librarymanagementsystem.domain.review.dto.user;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.review.entity.Review;

import lombok.Getter;

@Getter
public class UserReviewCreateResDTO {
  private Long id;
  
  private Long bookId;
  private String bookTitle;

  private Long userId;
  private String username;

  private LocalDateTime createdDate;

  public UserReviewCreateResDTO(Review review) {
    this.id = review.getId();
    
    this.bookId = review.getBook().getId();
    this.bookTitle = review.getBook().getTitle();
    
    this.userId = review.getUser().getId();
    this.username = review.getUser().getUsername();

    this.createdDate = review.getCreatedDate();
  }
}
