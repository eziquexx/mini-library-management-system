package com.jelee.librarymanagementsystem.domain.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.review.dto.user.UserReviewCreateResDTO;
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


  // 사용자: 책 리뷰 작성
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
}
