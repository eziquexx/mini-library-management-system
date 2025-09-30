package com.jelee.librarymanagementsystem.domain.user.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.enums.Role;

import lombok.Getter;

@Getter
public class AdminUserRoleUpdatedResDTO {
  private Long id;
  private String username;
  private Role role;
  private LocalDateTime updatedAt;

  public AdminUserRoleUpdatedResDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.role = user.getRole();
    this.updatedAt = user.getUpdatedAt();
  }
}
