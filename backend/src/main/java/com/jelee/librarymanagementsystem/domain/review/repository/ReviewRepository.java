package com.jelee.librarymanagementsystem.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jelee.librarymanagementsystem.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  
  // 사용자: 책 리뷰 작성
  // 사용자는 책 하나에 리뷰 한 개만 가능
  // userId와 bookId 중복 조회
  boolean existsByUser_IdAndBook_Id(Long userId, Long bookId);

  // 사용자: 책 리뷰 전체 목록 조회 (페이징)
  // userId로 책 리뷰 조회하여 페이징 처리
  Page<Review> findByUser_Id(Long userId, Pageable pageable);

  // 사용자: 책 리뷰 검색 (페이징)
  // keyword로 책 리뷰 조회하여 페이징 처리
  Page<Review> findByUser_IdAndBook_TitleContainingIgnoreCase(Long userId, String keyword, Pageable pageable);

  // 관리자: 책 리뷰 타입별 목록 조회 (페이징)
  // 책 제목 + 사용자 이름
  Page<Review> findByBook_TitleContainingIgnoreCaseOrUser_UsernameContainingIgnoreCase(String bookTitle, String username, Pageable pageable);
  // 책 제목
  Page<Review> findByBook_TitleContainingIgnoreCase(String bookTitle, Pageable pageable);

  // 사용자 이름
  Page<Review> findByUser_UsernameContainingIgnoreCase(String username, Pageable pageable);
}
