package com.jelee.librarymanagementsystem.domain.admin.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jelee.librarymanagementsystem.domain.admin.entity.Book;

public interface AdminBookRepository extends JpaRepository<Book, Long> {
  // 동일 ISBN 체크
  boolean existsByIsbn(String isbn);

  // 동일 location 체크
  boolean existsByLocation(String location);

  // 특정 도서 location 체크
  boolean existsByLocationAndIdNot(String location, Long id);

  // location 위치
  Book findByLocation(String location);

  // 키워드 검색
  List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String titleKeyword, String authorKeyword);
}
