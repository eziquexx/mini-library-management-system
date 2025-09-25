package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements ErrorCode {
  
  REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_6000", "error.review.not_found"),
  REVIEW_ALREADY_CREATED(HttpStatus.BAD_REQUEST, "REVIEW_6001", "error.review.already_created"),
  REVIEW_USER_MISMATCH(HttpStatus.BAD_REQUEST, "REVIEW_6002", "error.review.user_mismatch"),
  REVIEW_CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "REVIEW_6003", "error.review.content_required");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

}
