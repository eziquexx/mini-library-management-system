package com.jelee.librarymanagementsystem.domain.user.dto.client;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.user.entity.User;

import lombok.Getter;

@Getter
public class UpdatePasswordResDTO {
  private Long id;
  private String username;
  private LocalDateTime updatedAt;

  public UpdatePasswordResDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.updatedAt = user.getUpdatedAt();
  }
}
