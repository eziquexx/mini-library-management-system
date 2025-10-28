package com.jelee.librarymanagementsystem.domain.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.book.repository.BookRepository;
import com.jelee.librarymanagementsystem.domain.review.dto.all.AllReviewListResDTO;
import com.jelee.librarymanagementsystem.domain.review.entity.Review;
import com.jelee.librarymanagementsystem.domain.review.repository.ReviewRepository;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.BookErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AllReviewService {
  private final ReviewRepository reviewRepository;
  private final BookRepository bookRepository;

  /*
   * 공용: 특정 책 리뷰 전체 목록 (페이징)
   */
  public PageResponse<AllReviewListResDTO> allListReviews(Long bookId, int page, int size) {

    // 도서 확인
    bookRepository.findById(bookId)
      .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);
    
    // bookId 페이징 조회
    Page<Review> result = reviewRepository.findByBook_Id(bookId, pageable);

    // Page<Review> -> age<AllReviewListResDTO>로 맵핑
    Page<AllReviewListResDTO> pageDTO = result.map(AllReviewListResDTO::new);

    // 반환
    return new PageResponse<>(pageDTO);
  }
}
