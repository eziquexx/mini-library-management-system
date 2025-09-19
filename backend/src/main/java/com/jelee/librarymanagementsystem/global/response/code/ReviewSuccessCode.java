package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewSuccessCode implements SuccessCode  {
 
  REVIEW_CREATED(HttpStatus.CREATED, "REVIEW_200", "success.review.created"),
  REVIEW_LIST_FETCHED(HttpStatus.OK, "REVIEW_201", "success.review.list_fetched"),
  REVIEW_DETAIL(HttpStatus.OK, "REVIEW_202", "success.review.detail"),
  REVIEW_UPDATED(HttpStatus.OK, "REVIEW_203", "success.review.updated"),
  REVIEW_DELETED(HttpStatus.OK, "REVIEW_204", "success.review.deleted"),
  REVIEW_FETCHED(HttpStatus.OK, "REVIEW_205", "success.review.fetched");;

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
