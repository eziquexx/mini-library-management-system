package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum BookErrorCode implements ErrorCode {

  BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOK_3000", "error.book.not_found"),
  BOOK_TITLE_DUPLICATED(HttpStatus.CONFLICT, "BOOK_3001", "error.book.title.duplicate"),
  BOOK_ISBN_DUPLICATED(HttpStatus.CONFLICT, "BOOK_3002", "error.book.isbn.duplicate"),
  BOOK_LOCATION_DUPLICATED(HttpStatus.CONFLICT, "BOOK_3003", "error.book.location.duplicate"),
  BOOK_TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "BOOK_3004", "error.book.title.required"),
  BOOK_ISBN_REQUIRED(HttpStatus.BAD_REQUEST, "BOOK_3005", "error.book.isbn.required"),
  BOOK_AUTHOR_REQUIRED(HttpStatus.BAD_REQUEST, "BOOK_3006", "error.book.author.required"),
  BOOK_PUBLISHER_REQUIRED(HttpStatus.BAD_REQUEST, "BOOK_3007", "error.book.publisher.required"),
  BOOK_PUBLISHERDATE_REQUIRED(HttpStatus.BAD_REQUEST, "BOOK_3008", "error.book.publisherdate.required"),
  BOOK_DATE_INVALID(HttpStatus.BAD_REQUEST, "BOOK_3009", "error.book.date.invalid"),
  BOOK_LOCATION_REQUIRED(HttpStatus.BAD_REQUEST, "BOOK_3010", "error.book.location.required"),
  BOOK_DESCRIPTION_REQUIRED(HttpStatus.BAD_REQUEST, "BOOK_3011", "error.book.description.required"),
  BOOK_UPDATE_FALIED(HttpStatus.INTERNAL_SERVER_ERROR, "BOOK_3012", "error.book.update_failed"),
  BOOK_SEARCH_TYPE_FAILED(HttpStatus.BAD_REQUEST, "BOOK_3013", "error.book.search.type"),
  BOOK_ALREADY_LOST(HttpStatus.BAD_REQUEST, "BOOK_3014", "error.book.already_lost");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  BookErrorCode(HttpStatus httpStatus, String code, String message) {
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
