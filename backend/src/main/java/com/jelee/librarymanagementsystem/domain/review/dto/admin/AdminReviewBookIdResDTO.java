package com.jelee.librarymanagementsystem.domain.review.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.review.entity.Review;

import lombok.Getter;

@Getter
public class AdminReviewBookIdResDTO {
  private Long id;

  private Long userId;
  private String username;
  
  private Long bookId;
  private String bookTitle;
  private String bookIsbn;

  private String content;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  public AdminReviewBookIdResDTO(Review review) {
    this.id = review.getId();
    this.userId = review.getUser().getId();
    this.username = review.getUser().getUsername();
    this.bookId = review.getBook().getId();
    this.bookTitle = review.getBook().getTitle();
    this.bookIsbn = review.getBook().getIsbn();
    this.content = review.getContent();
    this.createdDate = review.getCreatedDate();
    this.updatedDate = review.getUpdatedDate();
  }
}
