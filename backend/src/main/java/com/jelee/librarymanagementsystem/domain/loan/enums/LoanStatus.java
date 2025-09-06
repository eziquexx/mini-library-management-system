package com.jelee.librarymanagementsystem.domain.loan.enums;

public enum LoanStatus {
  LOANED,     // 대출 중
  RETURNED,   // 반납 완료(대출 가능)
  OVERDUE,    // 연체됨(기한 내에 반납 안함)
  LOST        // 분실됨
}
