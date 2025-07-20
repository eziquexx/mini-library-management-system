package com.jelee.librarymanagementsystem.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private final MessageProvider messageProvider;

  public GlobalExceptionHandler(MessageProvider messageProvider) {
    this.messageProvider = messageProvider;
  }
  
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
    ErrorCode errorCode = ex.getErrorCode();
    String message = messageProvider.getMessage(errorCode.getMessage());
    ErrorResponse response = new ErrorResponse(errorCode.getCode(), message);

    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(response);
  }
}
