package com.jelee.librarymanagementsystem.domain.user.dto;

import com.jelee.librarymanagementsystem.global.enums.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRoleUpdateReqDTO {
  private Long userId;
  private Role role;
}
