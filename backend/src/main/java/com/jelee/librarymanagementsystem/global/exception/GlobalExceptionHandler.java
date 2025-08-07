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
  public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException ex) {
    ErrorCode errorCode = ex.getErrorCode();
    String message = messageProvider.getMessage(errorCode.getMessage());
    // ApiResponse response = new ApiResponse(errorCode.getCode(), message, null);
    // ApiResponse response = new ApiResponse<T>(errorCode.getCode(), message, message);

    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(ApiResponse.error(
          errorCode,
          message,
          null
        ));
  }
}
