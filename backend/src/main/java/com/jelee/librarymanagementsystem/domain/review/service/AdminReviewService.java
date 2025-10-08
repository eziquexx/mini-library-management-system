package com.jelee.librarymanagementsystem.domain.review.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.review.dto.admin.AdminReviewBookIdResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.admin.AdminReviewDeleteResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.admin.AdminReviewDetailResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.admin.AdminReviewListResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.admin.AdminReviewSearchResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.admin.AdminReviewUserIdResDTO;
import com.jelee.librarymanagementsystem.domain.review.entity.Review;
import com.jelee.librarymanagementsystem.domain.review.enums.ReviewSearchType;
import com.jelee.librarymanagementsystem.domain.review.repository.ReviewRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.AuthErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.ReviewErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminReviewService {
  
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;

  /*
   * 관리자: 책 리뷰 전체 목록 (페이징)
   */
  public PageResponse<AdminReviewListResDTO> allListReview(int page, int size, Long userId) {

    // 관리자 조회 및 권한 확인
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // 리뷰 전체 목록 Page 형태로 가져오기
    Page<Review> result = reviewRepository.findAll(pageable);
    
    // Page<Review> -> Page<AdminReviewListResDTO> 로 맵핑.
    Page<AdminReviewListResDTO> pageDTO = result.map(AdminReviewListResDTO::new);

    // 반환
    return new PageResponse<>(pageDTO);
  }

  /*
   * 관리자: 책 리뷰 타입별 검색 (페이징)
   */
  public PageResponse<AdminReviewSearchResDTO> typeSearchReview(ReviewSearchType type, String keyword, int page, int size, Long userId) {

    // 관리자 조회 및 권한 확인
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // 결과 담을 변수
    Page<Review> result;

    // 검색 타입
    String searchType = type.name();

    // 타입별 검색
    switch(searchType) {
      case "ALL":
        // 전체
        result = reviewRepository.findByBook_TitleContainingIgnoreCaseOrUser_UsernameContainingIgnoreCase(keyword, keyword, pageable);
        break;
      case "BOOKTITLE":
        // 책 제목
        result = reviewRepository.findByBook_TitleContainingIgnoreCase(keyword, pageable);
        break;
      case "USERNAME":
        // 사용자 이름
        result = reviewRepository.findByUser_UsernameContainingIgnoreCase(keyword, pageable);
        break;
      default:
        throw new IllegalArgumentException("Invalid search type: " + type);
    }

    // 값 없는 경우
    if (result.isEmpty()) {
      throw new BaseException(ReviewErrorCode.REVIEW_NOT_FOUND);
    }

    // Page<Review> -> Page<AdminReviewSearchResDTO> 맵핑
    Page<AdminReviewSearchResDTO> pageDTO = result.map(AdminReviewSearchResDTO::new);

    // 반환
    return new PageResponse<>(pageDTO);
  }

  /*
   * 관리자: 특정 도서 리뷰 목록 (페이징)
   */
  public PageResponse<AdminReviewBookIdResDTO> bookIdListReview(Long bookId, int page, int size, Long userId) {

    // 관리자 조회 및 권한 확인
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // Page형태로 결과 가져오기
    Page<Review> result = reviewRepository.findByBook_Id(bookId, pageable);

    // Page<Review> -> Page<AdminReviewBookIdResDTO> 맵핑
    Page<AdminReviewBookIdResDTO> pageDTO = result.map(AdminReviewBookIdResDTO::new);

    // 반환
    return new PageResponse<>(pageDTO);
  }

  // 관리자: 특정 사용자 리뷰 목록
  @Transactional
  public Page<AdminReviewUserIdResDTO> userIdListReview(Long userId, int page, int size, Long userGetId) {

    // 관리자 조회 및 권한 확인
    User user = userRepository.findById(userGetId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // Page형태로 결과 가져오기
    Page<Review> result = reviewRepository.findByUser_Id(userId, pageable);

    // PageDTO를 List 형태로 변환
    List<AdminReviewUserIdResDTO> listDTO = result.getContent()
        .stream()
        .map(AdminReviewUserIdResDTO::new)
        .toList();

    // PageImpl을 사용하여 ListDTO를 pageable로 랩핑하여 반환
    return new PageImpl<>(listDTO, result.getPageable(), result.getTotalElements());
  }

  // 관리자: 리뷰 상세
  @Transactional
  public AdminReviewDetailResDTO detailReview(Long reviewId, Long userId) {

    // 관리자 조회 및 권한 확인
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 리뷰 조회
    Review result = reviewRepository.findById(reviewId).orElseThrow(()-> new BaseException(ReviewErrorCode.REVIEW_NOT_FOUND));

    // 반환
    return new AdminReviewDetailResDTO(result);
  }

  // 관리자: 리뷰 삭제
  @Transactional
  public AdminReviewDeleteResDTO deleteReview(Long reviewId, Long usreId) {

    // 관리자 조회 및 권한 확인
    User user = userRepository.findById(usreId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 리뷰 조회
    Review delReview = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new BaseException(ReviewErrorCode.REVIEW_NOT_FOUND));

    Review tempReview = delReview;
    
    // 리뷰 삭제
    reviewRepository.delete(delReview);
    
    // 반환
    return new AdminReviewDeleteResDTO(tempReview);
  }
}
