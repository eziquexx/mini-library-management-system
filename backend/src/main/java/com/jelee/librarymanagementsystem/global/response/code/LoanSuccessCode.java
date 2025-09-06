package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum LoanSuccessCode implements SuccessCode {
  
  LOAN_CRATED_SUCCESS(HttpStatus.CREATED, "LOAN_200", "success.loan.created");

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
