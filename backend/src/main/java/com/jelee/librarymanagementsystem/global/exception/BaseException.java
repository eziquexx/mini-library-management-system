package com.jelee.librarymanagementsystem.global.exception;

import com.jelee.librarymanagementsystem.global.enums.ErrorCode;

public class BaseException extends RuntimeException {
  
  private final ErrorCode errorCode;

  public BaseException(ErrorCode errorCode) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
