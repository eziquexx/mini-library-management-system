package com.jelee.librarymanagementsystem.domain.review.dto.admin;

import com.jelee.librarymanagementsystem.domain.review.entity.Review;

import lombok.Getter;

@Getter
public class AdminReviewDeleteResDTO {
  private Long id;

  public AdminReviewDeleteResDTO(Review review) {
    this.id = review.getId();
  }
}
