package com.jelee.librarymanagementsystem.domain.user.dto;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserListResDTO {
  private long id;
  private String username;
  private String email;
  private Role role;
  private LocalDateTime joinDate;
  private LocalDateTime lastLoginDate;
  private UserStatus status;

  public UserListResDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.role = user.getRole();
    this.joinDate = user.getJoinDate();
    this.lastLoginDate = user.getLastLoginDate();
    this.status = user.getStatus();
  }
}
