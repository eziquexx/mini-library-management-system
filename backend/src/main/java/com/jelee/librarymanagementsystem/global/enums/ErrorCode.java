package com.jelee.librarymanagementsystem.global.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

  USER_USERNAME_DUPLICATED(HttpStatus.CONFLICT, "USER_001", "error.username.duplicate"),
  USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "USER_002", "error.email.duplicate"),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404", "error.user.not_found"),
  INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "USER_401", "error.user.invalid_password"),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_401", "error.unauthorized"),
  FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH403", "error.forbidden"),
  USER_EMAIL_SAME(HttpStatus.BAD_REQUEST, "USER_003", "error.email.same"),
  USER_PASSWORD_SAME(HttpStatus.BAD_REQUEST, "USER_004", "error.password.same"),
  USER_PASSWORD_NOTSAME(HttpStatus.BAD_REQUEST, "USER_005", "error.password.notsame"),
  //400
  BOOK_TITLE_BLANK(HttpStatus.BAD_REQUEST, "BOOK_001", "error.book.title.blank"),
  BOOK_AUTHOR_BLANK(HttpStatus.BAD_REQUEST, "BOOK_002", "error.book.author.blank"),
  BOOK_DATE_INVALID(HttpStatus.BAD_REQUEST, "BOOK_004", "error.book.date.invalid"),

  // 404
  BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOK_404", "error.book.not_found"),

  // 409
  BOOK_TITLE_DUPLICATED(HttpStatus.CONFLICT, "BOOK_409", "error.book.title.duplicate"),

  // 500
  BOOK_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "BOOK_500", "error.book.update_failed");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  ErrorCode(HttpStatus httpStatus, String code, String message) {
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
