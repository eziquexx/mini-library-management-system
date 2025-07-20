package com.jelee.librarymanagementsystem.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

<<<<<<< HEAD
import com.jelee.librarymanagementsystem.global.response.ErrorResponse;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private final MessageProvider messageProvider;

  public GlobalExceptionHandler(MessageProvider messageProvider) {
    this.messageProvider = messageProvider;
  }
=======
@RestControllerAdvice
public class GlobalExceptionHandler {
>>>>>>> origin/feature/exception
  
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
    ErrorCode errorCode = ex.getErrorCode();
<<<<<<< HEAD
    String message = messageProvider.getMessage(errorCode.getMessage());
    ErrorResponse response = new ErrorResponse(errorCode.getCode(), message);

    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(response);
=======

    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(new ErrorResponse(errorCode));
>>>>>>> origin/feature/exception
  }
}
