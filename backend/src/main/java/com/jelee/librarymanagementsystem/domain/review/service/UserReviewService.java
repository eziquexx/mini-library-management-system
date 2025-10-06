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
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewDeleteResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewDetailResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewListResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewSearchResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewUpdateResDTO;
import com.jelee.librarymanagementsystem.domain.review.entity.Review;
import com.jelee.librarymanagementsystem.domain.review.repository.ReviewRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
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

  /*
   * 사용자: 책 리뷰 작성
   */
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


  /*
   * 사용자: 책 리뷰 전체 목록 조회 (페이징)
   */
  public PageResponse<UserReviewListResDTO> allListReview(int page, int size, Long userId) {

    // 사용자 조회, 유효성 검사
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // userId로 책 리뷰 조회
    Page<Review> result = reviewRepository.findByUser_Id(user.getId(), pageable);

    // Page<Review> -> Page<UserReviewListResDTO> 맵핑 후 생성
    Page<UserReviewListResDTO> pageDTO = result.map(UserReviewListResDTO::new);

    // 반환
    return new PageResponse<>(pageDTO);
  }

  // 사용자: 책 리뷰 상세 조회
  @Transactional
  public UserReviewDetailResDTO detailReview(Long reviewId, Long userId) {

    // 사용자 조회 + 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    // 리뷰 조회 + 예외 처리
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new BaseException(ReviewErrorCode.REVIEW_NOT_FOUND));

    // 로그인한 사용자와 리뷰 작성자 검증 (본인 리뷰만 조회 가능)
    if (!user.getId().equals(review.getUser().getId())) {
      throw new BaseException(ReviewErrorCode.REVIEW_USER_MISMATCH);
    }

    // UserReviewDetailResDTO 객체로 반환
    return new UserReviewDetailResDTO(review);
  }

  // 사용자: 책 리뷰 수정
  @Transactional
  public UserReviewUpdateResDTO updateReview(Long reviewId, UserReviewUpdateReqDTO requestDTO, Long userId) {
    
    // 사용자 조회 + 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    // 리뷰 조회 + 예외 처리
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new BaseException(ReviewErrorCode.REVIEW_NOT_FOUND));

    // 로그인한 사용자와 리뷰 작성자 검증 (본인 리뷰만 수정 가능)
    if (!user.getId().equals(review.getUser().getId())) {
      throw new BaseException(ReviewErrorCode.REVIEW_USER_MISMATCH);
    }

    // 리뷰 내용 업데이트
    review.updateReview(requestDTO.getContent());
    
    // 반환
    return new UserReviewUpdateResDTO(review);
  }

  // 사용자: 책 리뷰 삭제
  @Transactional
  public UserReviewDeleteResDTO deleteReview(Long reviewId, Long userId) {

    // 사용자 조회 + 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    // 리뷰 조회 + 예외 처리
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new BaseException(ReviewErrorCode.REVIEW_NOT_FOUND));

    // 로그인한 사용자와 리뷰 작성자 검증 (본인 리뷰만 삭제 가능)
    if (!user.getId().equals(review.getUser().getId())) {
      throw new BaseException(ReviewErrorCode.REVIEW_USER_MISMATCH);
    }

    // 반환할 데이터 미리 저장
    UserReviewDeleteResDTO resopnseDTO= new UserReviewDeleteResDTO(review);

    // 리뷰 삭제
    reviewRepository.delete(review);

    return resopnseDTO;
  }

  // 사용자: 책 리뷰 검색
  public Page<UserReviewSearchResDTO> searchReview(String keyword, int page, int size, Long userId) {

    // 사용자 조회 + 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // 검색어 조회 + 페이지 처리
    Page<Review> result = keyword != null ? result = reviewRepository.findByUser_IdAndBook_TitleContainingIgnoreCase(user.getId(), keyword, pageable) : reviewRepository.findAll(pageable);

    // Page -> List 맵핑하여 변환
    List<UserReviewSearchResDTO> listDTO = result.getContent()
        .stream()
        .map(UserReviewSearchResDTO::new)
        .toList();

    // PageImpl로 감싸 Page 형태로 변환하여 반환
    return new PageImpl<>(listDTO, result.getPageable(), result.getTotalElements());
  }
}
