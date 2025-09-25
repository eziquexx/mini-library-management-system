package com.jelee.librarymanagementsystem.domain.auth.dto;

import com.jelee.librarymanagementsystem.domain.user.entity.User;

import lombok.Getter;

@Getter
public class LoginResDTO {
  private String username;
  private String token;

  public LoginResDTO(User user, String token) {
    this.username = user.getUsername();
    this.token = token;
  }
}
