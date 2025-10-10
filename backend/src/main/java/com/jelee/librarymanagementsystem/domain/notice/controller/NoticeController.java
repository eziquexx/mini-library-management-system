package com.jelee.librarymanagementsystem.domain.notice.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.notice.dto.client.UserNoticeDetailResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.client.UserNoticeListResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.client.UserNoticeSearchResDTO;
import com.jelee.librarymanagementsystem.domain.notice.service.NoticeService;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.NoticeSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {
  
  private final NoticeService userNoticeService;
  private final MessageProvider messageProvider;
  
  /*
   * 공용: 공지사항 전체 목록 보기 (페이징)
   */
  @GetMapping()
  public ResponseEntity<?> allListNotices(
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "10") int size) {

    // 서비스로직
    PageResponse<UserNoticeListResDTO> responseDTO = userNoticeService.allListNotices(page, size);

    // 성공메시지
    String message = messageProvider.getMessage(NoticeSuccessCode.NOTICE_LIST_FETCHED.getMessage());

    // 응답
    return ResponseEntity
              .status(NoticeSuccessCode.NOTICE_LIST_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                NoticeSuccessCode.NOTICE_LIST_FETCHED, 
                message, 
                responseDTO));
  }

  /*
   * 공용: 공지사항 상세 조회
   */
  @GetMapping("/{noticeId}")
  public ResponseEntity<?> detailNotice(@PathVariable("noticeId") Long noticeId) {

    // 서비스로직
    UserNoticeDetailResDTO responseDTO = userNoticeService.detailNotice(noticeId);

    // 성공메시지
    String message = messageProvider.getMessage(NoticeSuccessCode.NOTICE_FETCHED.getMessage());

    // 응답
    return ResponseEntity
              .status(NoticeSuccessCode.NOTICE_DELETED.getHttpStatus())
              .body(ApiResponse.success(
                NoticeSuccessCode.NOTICE_FETCHED, 
                message, 
                responseDTO));
  }

  // 공지사항 검색 목록 보기 (페이징)
  @GetMapping("/search")
  public ResponseEntity<?> searchNotices(
    @RequestParam(name = "keyword") String keyword, 
    @RequestParam(name = "page", defaultValue = "0") int page, 
    @RequestParam(name = "size", defaultValue = "10") int size) {

    // 서비스로직
    Page<UserNoticeSearchResDTO> responseDTO = userNoticeService.searchNotices(keyword, page, size);
    
    // 성공메시지
    String message = messageProvider.getMessage(NoticeSuccessCode.NOTICE_FETCHED.getMessage());

    return ResponseEntity
              .status(NoticeSuccessCode.NOTICE_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                NoticeSuccessCode.NOTICE_FETCHED, 
                message, 
                responseDTO));
  }
}
