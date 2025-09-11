package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum LoanErrorCode implements ErrorCode {

  LOAN_ALREADY_RETURNED(HttpStatus.BAD_REQUEST, "LOAN_401", "error.loan.already_returned"),
  LOAN_ALREADY_EXTENDED(HttpStatus.BAD_REQUEST, "LOAN_402", "error.loan.already_extended"),
  LOAN_CANNOT_BE_EXTENDED(HttpStatus.BAD_REQUEST, "LOAN_403","error.loan.cannot_extended"),
  LOAN_NOT_FOUND(HttpStatus.NOT_FOUND, "LOAN_404", "error.loan.not_found"),
  LOAN_ALREADY_BORROWED(HttpStatus.CONFLICT, "LOAN_407", "error.loan.already_borrowed"),
  LOAN_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "LOAN_406", "error.loan.limit_exceeded"),
  USER_NOT_ELIGIBLE_FOR_LOAN(HttpStatus.FORBIDDEN, "LOAN_408", "error.loan.user_not_eligible"),
  LOAN_SEARCH_TYPE_FAILED(HttpStatus.BAD_REQUEST, "LOAN_409", "error.loan.search.type");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  LoanErrorCode(HttpStatus httpStatus, String code, String message) {
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
