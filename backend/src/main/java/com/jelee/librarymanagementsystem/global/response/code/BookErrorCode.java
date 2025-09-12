package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum BookErrorCode implements ErrorCode {

  BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOK_404", "error.book.not_found"),
  BOOK_TITLE_DUPLICATED(HttpStatus.CONFLICT, "BOOK_001", "error.book.title.duplicate"),
  BOOK_ISBN_DUPLICATED(HttpStatus.CONFLICT, "BOOK_002", "error.book.isbn.duplicate"),
  BOOK_LOCATION_DUPLICATED(HttpStatus.CONFLICT, "BOOK_003", "error.book.location.duplicate"),
  BOOK_TITLE_BLANK(HttpStatus.BAD_REQUEST, "BOOK_004", "error.book.title.blank"),
  BOOK_ISBN_BLANK(HttpStatus.BAD_REQUEST, "BOOK_005", "error.book.isbn.blank"),
  BOOK_AUTHOR_BLANK(HttpStatus.BAD_REQUEST, "BOOK_006", "error.book.author.blank"),
  BOOK_PUBLISHER_BLANK(HttpStatus.BAD_REQUEST, "BOOK_007", "error.book.publisher.blank"),
  BOOK_PUBLISHERDATE_BLANK(HttpStatus.BAD_REQUEST, "BOOK_008", "error.book.publisherdate.blank"),
  BOOK_DATE_INVALID(HttpStatus.BAD_REQUEST, "BOOK_009", "error.book.date.invalid"),
  BOOK_LOCATION_BLANK(HttpStatus.BAD_REQUEST, "BOOK_010", "error.book.location.blank"),
  BOOK_DESCRIPTION_BLANK(HttpStatus.BAD_REQUEST, "BOOK_011", "error.book.description.blank"),
  BOOK_UPDATE_FALIED(HttpStatus.INTERNAL_SERVER_ERROR, "BOOK_500", "error.book.update_failed"),
  BOOK_SEARCH_TYPE_FAILED(HttpStatus.BAD_REQUEST, "BOOK_012", "error.book.search.type"),
  BOOK_ALREADY_LOSTED(HttpStatus.BAD_REQUEST, "BOOK_013", "error.book.already_losted");

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
