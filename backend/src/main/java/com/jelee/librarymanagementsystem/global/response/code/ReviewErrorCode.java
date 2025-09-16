package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements ErrorCode {
  
  REVIEW_ALREADY_CREATED(HttpStatus.BAD_REQUEST, "REVIEW_401", "error.review.already_created");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

}
