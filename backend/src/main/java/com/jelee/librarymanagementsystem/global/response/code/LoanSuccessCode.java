package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum LoanSuccessCode implements SuccessCode {
  
  LOAN_CREATED(HttpStatus.CREATED, "LOAN_5100", "success.loan.created"),
  LOAN_RETURNED(HttpStatus.OK, "LOAN_5101", "success.loan.returned"),
  LOAN_EXTENDED(HttpStatus.OK, "LOAN_5102", "success.loan.extended"),
  LOAN_MARKED_AS_LOST(HttpStatus.OK, "LOAN_5103", "success.loan.lost"),
  LOAN_FETCHED(HttpStatus.OK, "LOAN_5104", "success.loan.fetched"),
  LOAN_LIST_FETCHED(HttpStatus.OK, "LOAN_5105", "success.loan.list_fetched");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  LoanSuccessCode(HttpStatus httpStatus, String code, String message) {
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
