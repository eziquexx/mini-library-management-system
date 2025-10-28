package com.jelee.librarymanagementsystem.domain.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.review.dto.all.AllReviewListResDTO;
import com.jelee.librarymanagementsystem.domain.review.service.AllReviewService;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.ReviewSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AllReviewController {
  
  private final AllReviewService allReviewService;
  private final MessageProvider messageProvider;

  /*
   * 공용: 특정 책 리뷰 전체 목록 (페이징)
   */
  @GetMapping("/books/{bookId}/reviews")
  public ResponseEntity<?> allListReviews(
    @PathVariable("bookId") Long bookId,
    @RequestParam(value = "page", defaultValue = "0") int page, 
    @RequestParam(value = "size", defaultValue = "10") int size) {
    
      // 서비스로직
      PageResponse<AllReviewListResDTO> responseDTO = allReviewService.allListReviews(bookId, page, size);

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
}
