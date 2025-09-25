package com.jelee.librarymanagementsystem.domain.auth.dto;

import com.jelee.librarymanagementsystem.domain.user.entity.User;

import lombok.Getter;

@Getter
public class LogoutResDTO {
  private Long id;
  private String username;

  public LogoutResDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
  }
}
