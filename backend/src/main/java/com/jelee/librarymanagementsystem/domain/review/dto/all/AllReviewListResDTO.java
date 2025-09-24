package com.jelee.librarymanagementsystem.domain.review.dto.all;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.review.entity.Review;

import lombok.Getter;

@Getter
public class AllReviewListResDTO {
  private long id;
  private String bookTitle;
  private String username;
  private String content;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  public AllReviewListResDTO(Review review) {
    this.id = review.getId();
    this.bookTitle = review.getBook().getTitle();
    this.username = review.getUser().getUsername();
    this.content = review.getContent();
    this.createdDate = review.getCreatedDate();
    this.updatedDate = review.getUpdatedDate();
  }
}
