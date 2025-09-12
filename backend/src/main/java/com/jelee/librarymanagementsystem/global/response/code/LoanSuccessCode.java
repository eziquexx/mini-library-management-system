package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum LoanSuccessCode implements SuccessCode {
  
  LOAN_CRATED_SUCCESS(HttpStatus.CREATED, "LOAN_200", "success.loan.created"),
  LOAN_RETURNED_SUCCESS(HttpStatus.OK, "LOAN_201", "success.loan.returned"),
  LOAN_EXTENDED_SUCCESS(HttpStatus.OK, "LOAN_202", "success.loan.extended"),
  LOAN_MARKED_LOST_SUCCESS(HttpStatus.OK, "LOAN_203", "success.loan.lost"),
  LOAN_LIST_FETCHED(HttpStatus.OK, "LOAN_205", "success.loan.list_fetched"),
  LOAN_DETAIL_FETCHED_SUCCESS(HttpStatus.OK, "LOAN_206", "success.loan.deatil"),
  LOAN_FETCHED(HttpStatus.OK, "LOAN_207", "success.loan.fetched");

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
