package com.jelee.librarymanagementsystem.domain.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
  // 동일 ISBN 체크
  boolean existsByIsbn(String isbn);

  // 동일 location 체크
  boolean existsByLocation(String location);

  // 특정 도서 location 체크
  boolean existsByLocationAndIdNot(String location, Long id);

  // location 위치
  Book findByLocation(String location);

  // 키워드 검색
  // List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String titleKeyword, String authorKeyword);

  // 키워드 타입별 검색 - title, author (all)
  Page<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author, Pageable pageable);
  
  // 키워드 타입별 검색 - title
  Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

  // 키워드 타입별 검색 - author
  Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
}
