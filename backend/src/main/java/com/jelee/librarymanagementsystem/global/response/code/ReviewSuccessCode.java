package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewSuccessCode implements SuccessCode  {
 
  REVIEW_CREATED(HttpStatus.CREATED, "REVIEW_6100", "success.review.created"),
  REVIEW_UPDATED(HttpStatus.OK, "REVIEW_6101", "success.review.updated"),
  REVIEW_DELETED(HttpStatus.OK, "REVIEW_6102", "success.review.deleted"),
  REVIEW_FETCHED(HttpStatus.OK, "REVIEW_6103", "success.review.fetched"),
  REVIEW_LIST_FETCHED(HttpStatus.OK, "REVIEW_6104", "success.review.list_fetched");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  // ReviewSuccessCode(HttpStatus httpStatus, String code, String message) {
  //   this.httpStatus = httpStatus;
  //   this.code = code;
  //   this.message = message;
  // }

  // public HttpStatus geHttpStatus() {
  //   return httpStatus;
  // }

  // public String getCode() {
  //   return code;
  // }

  // public String getMessage() {
  //   return message;
  // }
}
