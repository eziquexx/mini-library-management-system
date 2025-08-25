package com.jelee.librarymanagementsystem.domain.user.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.jelee.librarymanagementsystem.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  // 아이디 중복 체크
  boolean existsByUsername(String username);

  // 이메일 중복 체크
  boolean existsByEmail(String email);

  // username으로 사용자 전체 엔티티 조회
  Optional<User> findByUsername(String username);

  // 관리자 - 사용자 전체 목록 조회 (+페이징)
  @NonNull
  Page<User> findAll(Pageable pageable);
}
