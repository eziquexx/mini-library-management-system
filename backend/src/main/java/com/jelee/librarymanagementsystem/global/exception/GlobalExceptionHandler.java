package com.jelee.librarymanagementsystem.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.ErrorCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private final MessageProvider messageProvider;

  public GlobalExceptionHandler(MessageProvider messageProvider) {
    this.messageProvider = messageProvider;
  }
  
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ApiResponse<?>> handleBaseException(BaseException ex) {
    ErrorCode errorCode = ex.getErrorCode();
    String message = messageProvider.getMessage(errorCode.getMessage());
    
    Object data = null;
    if (ex instanceof DataBaseException) {
      data = ((DataBaseException) ex).getData();
    }

    ApiResponse<?> response = ApiResponse.error(errorCode, message, data);

    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(response);
  }
}
