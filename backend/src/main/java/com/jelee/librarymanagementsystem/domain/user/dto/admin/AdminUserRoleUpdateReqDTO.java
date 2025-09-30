package com.jelee.librarymanagementsystem.domain.user.dto.admin;

import com.jelee.librarymanagementsystem.global.enums.Role;

import lombok.Getter;

@Getter
public class AdminUserRoleUpdateReqDTO {
  private Role role;
}
