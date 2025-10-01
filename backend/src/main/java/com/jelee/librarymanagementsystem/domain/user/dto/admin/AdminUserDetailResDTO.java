package com.jelee.librarymanagementsystem.domain.user.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;

import lombok.Getter;

@Getter
public class AdminUserDetailResDTO {
  private Long id;
  private String username;
  private String email;
  private Role role;
  private LocalDateTime joinDate;
  private LocalDateTime lastLoginDate;
  private LocalDateTime updatedAt;
  private LocalDateTime inactiveAt;
  private LocalDateTime deletedAt;
  private UserStatus status;

  public AdminUserDetailResDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.role = user.getRole();
    this.joinDate = user.getJoinDate();
    this.lastLoginDate = user.getLastLoginDate();
    this.updatedAt = user.getUpdatedAt();
    this.inactiveAt = user.getInactiveAt();
    this.deletedAt = user.getDeletedAt();
    this.status = user.getStatus();
  }
}
