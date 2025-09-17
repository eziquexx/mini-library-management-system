package com.jelee.librarymanagementsystem.domain.review.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;
import com.jelee.librarymanagementsystem.domain.book.repository.BookRepository;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewCreateResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewListResDTO;
import com.jelee.librarymanagementsystem.domain.review.entity.Review;
import com.jelee.librarymanagementsystem.domain.review.repository.ReviewRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.BookErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.ReviewErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserReviewService {
  
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;
  private final BookRepository bookRepository;

  // 사용자: 책 리뷰 작성
  @Transactional
  public UserReviewCreateResDTO createReview(Long bookId, UserReviewCreateReqDTO requestDTO, Long userId) {

    // 사용자 유효 검사 + 예외
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    // 책 유효 검사 + 예외
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));
    
    // 리뷰 유효 검사
    // 사용자는 도서 하나에 리뷰 하나만 작성 가능
    if (reviewRepository.existsByUser_IdAndBook_Id(user.getId(), book.getId())) {
      throw new BaseException(ReviewErrorCode.REVIEW_ALREADY_CREATED);
    }

    // Review 엔티티 객체 생성
    Review review = Review.builder()
        .user(user)
        .book(book)
        .content(requestDTO.getContent())
        .build();

    // DB에 저장
    reviewRepository.save(review);

    // 반환
    return new UserReviewCreateResDTO(review);
  }


  // 사용자: 책 리뷰 전체 목록 조회 (페이징)
  public Page<UserReviewListResDTO> allListReview(int page, int size, Long userId) {

    // 사용자 조회, 유효성 검사
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // userId로 책 리뷰 조회
    Page<Review> result = reviewRepository.findByUser_Id(user.getId(), pageable);

    // Page DTO를 List DTO로 형변환.
    List<UserReviewListResDTO> listDTO = result.getContent()
        .stream()
        .map(UserReviewListResDTO::new)
        .toList();

    // List DTO를 PageImpl로 감싸서 페이징 형태로 반환.
    return new PageImpl<>(listDTO, result.getPageable(), result.getTotalElements());
  }
}
