package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements ErrorCode {
  
  REVIEW_ALREADY_CREATED(HttpStatus.BAD_REQUEST, "REVIEW_401", "error.review.already_created"),
  REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_404", "error.review.not_found"),
  REVIEW_USER_NOT_SAME(HttpStatus.BAD_REQUEST, "REVIEW_402", "error.review.user_not_same"),
  REVIEW_CONENT_NOT_BLANK(HttpStatus.BAD_REQUEST, "REVIEW_403", "error.review.update_content_not_blank");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

}
