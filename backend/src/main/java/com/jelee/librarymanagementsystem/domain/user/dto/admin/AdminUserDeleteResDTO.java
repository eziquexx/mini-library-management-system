package com.jelee.librarymanagementsystem.domain.user.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;

import lombok.Getter;

@Getter
public class AdminUserDeleteResDTO {
  private Long id;
  private String username;
  private String email;
  private LocalDateTime updatedAt;
  private UserStatus status;

  public AdminUserDeleteResDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.updatedAt = user.getUpdatedAt();
    this.status = user.getStatus();
  }
}
