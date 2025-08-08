package com.jelee.librarymanagementsystem.domain.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jelee.librarymanagementsystem.domain.admin.entity.Book;

public interface AdminBookRepository extends JpaRepository<Book, Long> {
  // 동일 ISBN 체크
  boolean existsByIsbn(String isbn);

  // 동일 location 체크
  boolean existsByLocation(String location);

  // location 위치
  Book findByLocation(String location);
}
