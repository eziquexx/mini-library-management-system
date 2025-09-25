package com.jelee.librarymanagementsystem.domain.auth.dto;

import com.jelee.librarymanagementsystem.domain.user.entity.User;

import lombok.Getter;

@Getter
public class JoinResDTO {
  private Long id;
  private String username;

  public JoinResDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
  }
}
