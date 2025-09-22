package com.jelee.librarymanagementsystem.domain.review.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.review.dto.admin.AdminReviewListResDTO;
import com.jelee.librarymanagementsystem.domain.review.entity.Review;
import com.jelee.librarymanagementsystem.domain.review.repository.ReviewRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.AuthErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminReviewService {
  
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;

  // 관리자: 책 리뷰 전체 목록
  public Page<AdminReviewListResDTO> allListReview(int page, int size, Long userId) {

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
    
    // Page 결과를 List 형태로 변환
    List<AdminReviewListResDTO> listDTO = result.getContent()
        .stream()
        .map(AdminReviewListResDTO::new)
        .toList();

    // PageImpl을 사용하여 Page 형태로 감싸서 반환
    return new PageImpl<>(listDTO, result.getPageable(), result.getTotalElements());
  }
}
