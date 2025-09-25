package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum BookSuccessCode implements SuccessCode {
  BOOK_CREATED(HttpStatus.CREATED, "BOOK_3100", "success.book.created"),
  BOOK_UPDATED(HttpStatus.OK, "BOOK_3101", "success.book.updated"),
  BOOK_DELETED(HttpStatus.OK, "BOOK_3102", "success.book.deleted"),
  BOOK_FETCHED(HttpStatus.OK, "BOOK_3103", "success.book.fetched"),
  BOOK_LIST_FETCHED(HttpStatus.OK, "BOOK_3104", "success.book.list_fetched");

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
