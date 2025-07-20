package com.jelee.librarymanagementsystem.global.exception;

public class BaseException extends RuntimeException {
  
  private final ErrorCode errorCode;

  public BaseException(ErrorCode errorCode) {
<<<<<<< HEAD
    super(errorCode.getCode()); //errorCode.getCode()
=======
    super(errorCode.getMessage());
>>>>>>> origin/feature/exception
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
