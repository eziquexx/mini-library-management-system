package com.jelee.librarymanagementsystem.domain.user.dto.client;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;

import lombok.Getter;

@Getter
public class DeleteAccountResDTO {
  private Long id;
  private String username;
  private UserStatus status;
  private LocalDateTime inactiveAt;

  public DeleteAccountResDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.status = user.getStatus();
    this.inactiveAt = user.getInactiveAt();
  }
}
