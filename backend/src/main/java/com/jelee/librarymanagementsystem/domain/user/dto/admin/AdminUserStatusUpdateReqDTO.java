package com.jelee.librarymanagementsystem.domain.user.dto.admin;

import com.jelee.librarymanagementsystem.global.enums.UserStatus;

import lombok.Getter;

@Getter
public class AdminUserStatusUpdateReqDTO {
  private UserStatus status;
}
