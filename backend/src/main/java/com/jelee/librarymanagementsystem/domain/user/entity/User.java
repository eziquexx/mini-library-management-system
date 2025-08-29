package com.jelee.librarymanagementsystem.domain.user.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {
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
  private LocalDateTime updatedAt;
  private LocalDateTime inactiveAt;
  private LocalDateTime deletedAt;

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

  // --- UserDetails 인터페이스 구현
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (role == null) {
      return Collections.emptyList();
    }

    return Collections.singleton(() -> role.toString());
  }

  @Override
  public String getUsername() { 
    return username; 
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
