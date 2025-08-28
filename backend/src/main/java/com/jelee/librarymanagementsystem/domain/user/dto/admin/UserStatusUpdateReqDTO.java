package com.jelee.librarymanagementsystem.domain.user.dto.admin;

import com.jelee.librarymanagementsystem.global.enums.UserStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserStatusUpdateReqDTO {
  private Long id;
  private UserStatus status;
}
