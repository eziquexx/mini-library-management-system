package com.jelee.librarymanagementsystem.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jelee.librarymanagementsystem.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  
  boolean existsByUser_IdAndBook_Id(Long userId, Long bookId);
}
