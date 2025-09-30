package com.jelee.librarymanagementsystem.domain.user.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;

import lombok.Getter;

@Getter
public class AdminUserStatusUpdateResDTO {
  private Long id;
  private String username;
  private UserStatus status;
  private LocalDateTime updatedAt;

  public AdminUserStatusUpdateResDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.status = user.getStatus();
    this.updatedAt = user.getUpdatedAt();
  }
}
