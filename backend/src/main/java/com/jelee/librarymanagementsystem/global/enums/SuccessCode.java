package com.jelee.librarymanagementsystem.global.enums;

import org.springframework.http.HttpStatus;

public enum SuccessCode {

  USER_CREATED(HttpStatus.CREATED, "USER_201", "success.user.signup"),
  USER_LOGIN_SUCCESS(HttpStatus.OK, "AUTH_200", "success.user.login"),
  USER_LOGOUT_SUCCESS(HttpStatus.OK, "AUTH_200", "success.user.logout"),
  USER_AUTHORIZED_SUCCESS(HttpStatus.OK, "AUTH_200", "success.user.authorized"),
  BOOK_REGISTERED(HttpStatus.CREATED, "BOOK_201", "success.book.registered"),
  USER_EMAIL_UPDATE_SUCCESS(HttpStatus.OK, "AUTH_200", "success.user.email.update"),
  USER_PASSWORD_UPDATE_SUCCESS(HttpStatus.OK, "AUTH_200", "success.user.password.update"),
  USER_DELETE_ACCOUNT_SUCCESS(HttpStatus.OK, "AUTH_200", "success.user.delete.account"),
  BOOK_CREATED(HttpStatus.CREATED, "BOOK_201", "도서가 성공적으로 등록되었습니다."),
  BOOK_UPDATED(HttpStatus.OK, "BOOK_200", "도서 수정이 완료되었습니다."),
  BOOK_DELETED(HttpStatus.OK, "BOOK_204", "도서가 삭제되었습니다."),
  BOOK_FETCHED(HttpStatus.OK, "BOOK_210", "도서 조회가 완료되었습니다."),
  BOOK_LIST_FETCHED(HttpStatus.OK, "BOOK_211", "도서 목록 조회가 완료되었습니다.");
 
  // 필드
  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  SuccessCode(HttpStatus httpStatus, String code, String message) {
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
