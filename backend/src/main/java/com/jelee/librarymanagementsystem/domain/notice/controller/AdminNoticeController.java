package com.jelee.librarymanagementsystem.domain.notice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeCreateResDTO;
import com.jelee.librarymanagementsystem.domain.notice.service.AdminNoticeService;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.NoticeSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/notices")
@RequiredArgsConstructor
public class AdminNoticeController {
  
  private final AdminNoticeService adminNoticeService;
  private final MessageProvider messageProvider;
  
  // 공지사항 등록
  @PostMapping()
  public ResponseEntity<?> createNotice(
    @RequestBody AdminNoticeCreateReqDTO requestDTO,
    @AuthenticationPrincipal User user) {

    // 서비스로직
    AdminNoticeCreateResDTO responseDTO = adminNoticeService.createNotice(requestDTO, user);

    // 성공 메시지
    String message = messageProvider.getMessage(NoticeSuccessCode.NOTICE_CREATED.getMessage());

    return ResponseEntity
              .status(NoticeSuccessCode.NOTICE_CREATED.getHttpStatus())
              .body(ApiResponse.success(
                NoticeSuccessCode.NOTICE_CREATED, 
                message, 
                responseDTO));
  }

  // 공지사항 수정

  // 공지사항 삭제

  // 공지사항 전체 목록 조회(페이징)

  // 공지사항 키워드 검색 조회(페이징)

  // 공지사항 상세
}
