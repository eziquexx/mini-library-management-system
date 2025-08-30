package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum BookSuccessCode implements SuccessCode {
  BOOK_CREATED(HttpStatus.CREATED, "BOOK_201", "success.book.created"),
  BOOK_UPDATED(HttpStatus.OK, "BOOK_200", "success.book.updated"),
  BOOK_DELETED(HttpStatus.OK, "BOOK_204", "success.book.deleted"),
  BOOK_FETCHED(HttpStatus.OK, "BOOK_210", "success.book.fetched"),
  BOOK_LIST_FETCHED(HttpStatus.OK, "BOOK_211", "success.book.list_fetched"),
  BOOK_DETAIL(HttpStatus.OK, "BOOK_212", "success.book.detail");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  BookSuccessCode(HttpStatus httpStatus, String code, String message) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.message = message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
