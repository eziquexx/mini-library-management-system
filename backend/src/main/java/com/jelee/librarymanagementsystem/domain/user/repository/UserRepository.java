package com.jelee.librarymanagementsystem.domain.user.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;

public interface UserRepository extends JpaRepository<User, Long> {
  
  // 아이디 중복 체크
  boolean existsByUsername(String username);

  // 이메일 중복 체크
  boolean existsByEmail(String email);

  // username으로 사용자 전체 엔티티 조회
  Optional<User> findByUsername(String username);

  // 관리자 - 사용자 전체 목록 조회 (+페이징)
  // Page<User> findAll(Pageable pageable);

  // 관리자 - 회원 username 검색 (+페이징)
  Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

  // 관리자 - 회원 email 검색 (+페이징)
  Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);

  // 사용자 - INACTIVE 상태 변경시 inactiveAt 조회
  List<User> findByStatusAndInactiveAtBefore(UserStatus status, LocalDateTime inactiveAt);
}
