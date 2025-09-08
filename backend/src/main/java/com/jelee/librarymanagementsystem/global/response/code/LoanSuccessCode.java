package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum LoanSuccessCode implements SuccessCode {
  
  LOAN_CRATED_SUCCESS(HttpStatus.CREATED, "LOAN_200", "success.loan.created"),
  LOAN_LIST_FETCHED(HttpStatus.OK, "LOAN_205", "success.loan.list_fetched"),
  LOAN_DETAIL_FETCHED_SUCCESS(HttpStatus.OK, "LOAN_206", "success.loan.deatil");

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
