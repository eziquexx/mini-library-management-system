package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import lombok.Getter;

@Getter
public class AdminLoanCreateReqDTO {
  private Long userId;
  private Long bookId;
}
