package com.jelee.librarymanagementsystem.domain.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewCreateResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewDeleteResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewDetailResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewListResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewSearchResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewUpdateResDTO;
import com.jelee.librarymanagementsystem.domain.review.service.UserReviewService;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.ReviewSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserReviewController {
  
  private final UserReviewService userReviewService;
  private final MessageProvider messageProvider;


  /*
   * 사용자: 책 리뷰 작성
   */
  @PostMapping("/books/{bookId}/reviews")
  public ResponseEntity<?> createReview(
    @PathVariable("bookId") Long bookId,
    @RequestBody UserReviewCreateReqDTO requestDTO,
    @AuthenticationPrincipal User user) {

    // 서비스로직
    UserReviewCreateResDTO responseDTO = userReviewService.createReview(bookId, requestDTO, user.getId());
    
    // 성공메시지
    String message = messageProvider.getMessage(ReviewSuccessCode.REVIEW_CREATED.getMessage());

    // 응답
    return ResponseEntity
              .status(ReviewSuccessCode.REVIEW_CREATED.getHttpStatus())
              .body(ApiResponse.success(
                ReviewSuccessCode.REVIEW_CREATED, 
                message, 
                responseDTO));
  }

  // 사용자: 책 리뷰 전체 목록 조회 (페이징)
  @GetMapping("/me/reviews")
  public ResponseEntity<?> allListReview(
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size,
    @AuthenticationPrincipal User user) {
    
    // 서비스로직
    Page<UserReviewListResDTO> responseDTO = userReviewService.allListReview(page, size, user.getId());

    // 성공메시지
    String message = messageProvider.getMessage(ReviewSuccessCode.REVIEW_LIST_FETCHED.getMessage());
    
    return ResponseEntity
              .status(ReviewSuccessCode.REVIEW_LIST_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                ReviewSuccessCode.REVIEW_LIST_FETCHED, 
                message, 
                responseDTO));
  }

  // 사용자: 책 리뷰 상세 조회
  @GetMapping("/me/reviews/{reviewId}")
  public ResponseEntity<?> detailReview(
    @PathVariable("reviewId") Long reviewId, 
    @AuthenticationPrincipal User user) {
    
      // 서비스로직
      UserReviewDetailResDTO responseDTO = userReviewService.detailReview(reviewId, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(ReviewSuccessCode.REVIEW_FETCHED.getMessage());

      // 반환
      return ResponseEntity
          .status(ReviewSuccessCode.REVIEW_FETCHED.getHttpStatus())
          .body(ApiResponse.success(
            ReviewSuccessCode.REVIEW_FETCHED, 
            message, 
            responseDTO));
  }

  // 사용자: 책 리뷰 수정
  @PatchMapping("/me/reviews/{reviewId}")
  public ResponseEntity<?> updateReview(
    @PathVariable("reviewId") Long reviewId,
    @RequestBody UserReviewUpdateReqDTO requestDTO, 
    @AuthenticationPrincipal User user) {

      // 서비스로직
      UserReviewUpdateResDTO responseDTO = userReviewService.updateReview(reviewId, requestDTO, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(ReviewSuccessCode.REVIEW_UPDATED.getMessage());
    
      return ResponseEntity
                .status(ReviewSuccessCode.REVIEW_UPDATED.getHttpStatus())
                .body(ApiResponse.success(
                  ReviewSuccessCode.REVIEW_UPDATED, 
                  message, 
                  responseDTO));
  }

  // 사용자: 책 리뷰 삭제
  @DeleteMapping("/me/reviews/{reviewId}")
  public ResponseEntity<?> deleteReview(
    @PathVariable("reviewId") Long reviewId, 
    @AuthenticationPrincipal User user) {
    
      // 서비스로직
      UserReviewDeleteResDTO responseDTO = userReviewService.deleteReview(reviewId, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(ReviewSuccessCode.REVIEW_DELETED.getMessage());

      // 반환
      return ResponseEntity
                .status(ReviewSuccessCode.REVIEW_DELETED.getHttpStatus())
                .body(ApiResponse.success(
                  ReviewSuccessCode.REVIEW_DELETED, 
                  message, 
                  responseDTO));
  }

  // 사용자: 책 리뷰 검색
  @GetMapping("/me/reviews/search")
  public ResponseEntity<?> searchReview(
    @RequestParam(name = "keyword", required = false) String keyword,
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size,
    @AuthenticationPrincipal User user) {

      // 서비스로직
      Page<UserReviewSearchResDTO> responseDTO = userReviewService.searchReview(keyword, page, size, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(ReviewSuccessCode.REVIEW_FETCHED.getMessage());

      // 반환
      return ResponseEntity
                .status(ReviewSuccessCode.REVIEW_FETCHED.getHttpStatus())
                .body(ApiResponse.success(
                  ReviewSuccessCode.REVIEW_FETCHED, 
                  message, 
                  responseDTO));
  }
}
