package com.jelee.librarymanagementsystem.domain.user.entity;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;
  private String email;

  @Enumerated(EnumType.STRING)
  private Role role;

  private LocalDateTime joinDate;
  private LocalDateTime lastLoginDate;

  @Enumerated(EnumType.STRING)
  private UserStatus status;

  @PrePersist
  public void prePersist() {
    this.joinDate = LocalDateTime.now();
    this.status = UserStatus.ACTIVE;
  }

  @PreUpdate
  public void preUpdate() {
    this.lastLoginDate = LocalDateTime.now();
  }
}
