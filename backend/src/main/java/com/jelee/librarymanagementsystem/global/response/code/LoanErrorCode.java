package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum LoanErrorCode implements ErrorCode {
  
  LOAN_NOT_FOUND(HttpStatus.NOT_FOUND, "LOAN_5000", "error.loan.not_found"),
  LOAN_ALREADY_BORROWED(HttpStatus.CONFLICT, "LOAN_5001", "error.loan.already_borrowed"),
  LOAN_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "LOAN_5002", "error.loan.limit_exceeded"),
  LOAN_USER_INELIGIBLE(HttpStatus.FORBIDDEN, "LOAN_5003", "error.loan.user_ineligible"),
  LOAN_SEARCH_TYPE_INVALID(HttpStatus.BAD_REQUEST, "LOAN_5004", "error.loan.search_type_invalid"),
  LOAN_ALREADY_RETURNED(HttpStatus.BAD_REQUEST, "LOAN_5005", "error.loan.already_returned"),
  LOAN_ALREADY_EXTENDED(HttpStatus.BAD_REQUEST, "LOAN_5006", "error.loan.already_extended"),
  LOAN_CANNOT_EXTEND(HttpStatus.BAD_REQUEST, "LOAN_5007","error.loan.cannot_extend"),
  LOAN_ALREADY_LOST(HttpStatus.BAD_REQUEST, "LOAN_5008", "error.loan.already_lost"),
  LOAN_STATUS_INVALID_FOR_LOST(HttpStatus.BAD_REQUEST, "LOAN_5009", "error.loan.status_invalid_for_lost"),
  LOAN_USER_NOT_SAME(HttpStatus.BAD_REQUEST, "LOAN_5010", "error.loan.user.not_same");

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
