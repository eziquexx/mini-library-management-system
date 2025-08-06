package com.jelee.librarymanagementsystem.global.enums;

public enum BookStatus {
  AVAILABLE,  // 예약 가능
  BORROWED,   // 대출 중 (대출 불가)
  RESERVED,   // 예약 중 (대출 불가)
  LOST        // 분실
}
