package com.jelee.librarymanagementsystem.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
    ErrorCode errorCode = ex.getErrorCode();

    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(new ErrorResponse(errorCode));
  }
}
