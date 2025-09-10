package com.jelee.librarymanagementsystem.domain.user.dto.client;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.user.entity.User;

import lombok.Getter;

@Getter
public class UserInfoResDTO {
  private String username;
  private String email;
  private LocalDateTime joinDate;
  private LocalDateTime lastLoginDate;

  public UserInfoResDTO(User user) {
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.joinDate = user.getJoinDate();
    this.lastLoginDate = user.getLastLoginDate();
  }
}
