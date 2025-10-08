package com.jelee.librarymanagementsystem.domain.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.review.dto.admin.AdminReviewBookIdResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.admin.AdminReviewDeleteResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.admin.AdminReviewDetailResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.admin.AdminReviewListResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.admin.AdminReviewSearchResDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.admin.AdminReviewUserIdResDTO;
import com.jelee.librarymanagementsystem.domain.review.enums.ReviewSearchType;
import com.jelee.librarymanagementsystem.domain.review.service.AdminReviewService;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.ReviewSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminReviewController {
  
  private final AdminReviewService adminReviewService;
  private final MessageProvider messageProvider;

  /*
   * 관리자: 책 리뷰 전체 목록 (페이징)
   */
  @GetMapping("/reviews")
  public ResponseEntity<?> allListReview(
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "10") int size,
    @AuthenticationPrincipal User user) {
    
      // 서비스로직
      PageResponse<AdminReviewListResDTO> responseDTO = adminReviewService.allListReview(page, size, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(ReviewSuccessCode.REVIEW_LIST_FETCHED.getMessage());
    
      // 응답
      return ResponseEntity
                .status(ReviewSuccessCode.REVIEW_LIST_FETCHED.getHttpStatus())
                .body(ApiResponse.success(
                  ReviewSuccessCode.REVIEW_LIST_FETCHED, 
                  message, 
                  responseDTO));
  }

  /*
   * 관리자: 책 리뷰 타입별 검색 (페이징)
   */
  @GetMapping("/reviews/search")
  public ResponseEntity<?> typeSearchReview(
    @RequestParam(value = "type", defaultValue = "ALL") ReviewSearchType type, 
    @RequestParam(value = "keyword") String keyword,
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "10") int size,
    @AuthenticationPrincipal User user) {

      // 서비스로직
      PageResponse<AdminReviewSearchResDTO> responseDTO = adminReviewService.typeSearchReview(type, keyword, page, size, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(ReviewSuccessCode.REVIEW_LIST_FETCHED.getMessage());

      // 응답
      return ResponseEntity
                .status(ReviewSuccessCode.REVIEW_LIST_FETCHED.getHttpStatus())
                .body(ApiResponse.success(
                  ReviewSuccessCode.REVIEW_LIST_FETCHED,
                  message,
                  responseDTO));
  }

  /*
   * 관리자: 특정 도서 리뷰 목록 (페이징)
   */
  @GetMapping("/books/{bookId}/reviews")
  public ResponseEntity<?> bookIdListReview(
    @PathVariable("bookId") Long bookId, 
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "10") int size,
    @AuthenticationPrincipal User user) {

      // 서비스로직
      PageResponse<AdminReviewBookIdResDTO> responseDTO = adminReviewService.bookIdListReview(bookId, page, size, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(ReviewSuccessCode.REVIEW_LIST_FETCHED.getMessage());

      // 응답
      return ResponseEntity
                .status(ReviewSuccessCode.REVIEW_LIST_FETCHED.getHttpStatus())
                .body(ApiResponse.success(
                  ReviewSuccessCode.REVIEW_LIST_FETCHED, 
                  message, 
                  responseDTO));
  }

  /*
   * 관리자: 특정 사용자 리뷰 목록 (페이징)
   */
  @GetMapping("/users/{userId}/reviews")
  public ResponseEntity<?> userIdListReview(
    @PathVariable("userId") Long userId, 
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "10") int size,
    @AuthenticationPrincipal User user) {

      // 서비스로직
      PageResponse<AdminReviewUserIdResDTO> responseDTO = adminReviewService.userIdListReview(userId, page, size, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(ReviewSuccessCode.REVIEW_LIST_FETCHED.getMessage());

      // 응답
      return ResponseEntity
                .status(ReviewSuccessCode.REVIEW_LIST_FETCHED.getHttpStatus())
                .body(ApiResponse.success(
                  ReviewSuccessCode.REVIEW_LIST_FETCHED, 
                  message, 
                  responseDTO));
  }

  /*
   * 관리자: 리뷰 상세
   */
  @GetMapping("/reviews/{reviewId}")
  public ResponseEntity<?> detailReview(
    @PathVariable("reviewId") Long reviewId,
    @AuthenticationPrincipal User user) {
    
      // 서비스로직
      AdminReviewDetailResDTO responseDTO = adminReviewService.detailReview(reviewId, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(ReviewSuccessCode.REVIEW_FETCHED.getMessage());

      // 응답
      return ResponseEntity
                .status(ReviewSuccessCode.REVIEW_FETCHED.getHttpStatus())
                .body(ApiResponse.success(
                  ReviewSuccessCode.REVIEW_FETCHED, 
                  message, 
                  responseDTO));
  }

  /*
   * 관리자: 리뷰 삭제
   */
  @DeleteMapping("/reviews/{reviewId}")
  public ResponseEntity<?> deleteReview(
    @PathVariable("reviewId") Long reviewId,
    @AuthenticationPrincipal User user) {
      
      // 서비스로직
      AdminReviewDeleteResDTO responseDTO = adminReviewService.deleteReview(reviewId, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(ReviewSuccessCode.REVIEW_DELETED.getMessage());

      // 응답
      return ResponseEntity
                .status(ReviewSuccessCode.REVIEW_DELETED.getHttpStatus())
                .body(ApiResponse.success(
                  ReviewSuccessCode.REVIEW_DELETED, 
                  message, 
                  responseDTO));
  }
}
