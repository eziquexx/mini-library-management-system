package com.jelee.librarymanagementsystem.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jelee.librarymanagementsystem.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
