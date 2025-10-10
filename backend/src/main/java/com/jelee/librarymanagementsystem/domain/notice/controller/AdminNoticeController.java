package com.jelee.librarymanagementsystem.domain.notice.controller;

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

import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeCreateResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeDetailResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeListResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeSearchResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeUpdateResDTO;
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
  
  /*
   * 관리자: 공지사항 등록
   */
  @PostMapping()
  public ResponseEntity<?> createNotice(
    @RequestBody AdminNoticeCreateReqDTO requestDTO,
    @AuthenticationPrincipal User user) {

    // 서비스로직
    AdminNoticeCreateResDTO responseDTO = adminNoticeService.createNotice(requestDTO, user.getId());

    // 성공 메시지
    String message = messageProvider.getMessage(NoticeSuccessCode.NOTICE_CREATED.getMessage());

    // 응답
    return ResponseEntity
              .status(NoticeSuccessCode.NOTICE_CREATED.getHttpStatus())
              .body(ApiResponse.success(
                NoticeSuccessCode.NOTICE_CREATED, 
                message, 
                responseDTO));
  }

  /*
   * 관리자: 공지사항 수정
   */
  @PatchMapping("/{noticeId}")
  public ResponseEntity<?> updateNotice(
    @PathVariable("noticeId") Long noticeId, 
    @RequestBody AdminNoticeUpdateReqDTO requestDTO, 
    @AuthenticationPrincipal User user) {

    // 서비스로직
    AdminNoticeUpdateResDTO responseDTO = adminNoticeService.updateNotice(noticeId, requestDTO, user.getId());

    // 성공메시지
    String message = messageProvider.getMessage(NoticeSuccessCode.NOTICE_UPDATED.getMessage());

    // 응답
    return ResponseEntity
              .status(NoticeSuccessCode.NOTICE_UPDATED.getHttpStatus())
              .body(ApiResponse.success(
                NoticeSuccessCode.NOTICE_UPDATED, 
                message, 
                responseDTO));
  }

  // 공지사항 삭제
  @DeleteMapping("/{noticeId}")
  public ResponseEntity<?> deleteNotice(@PathVariable("noticeId") Long noticeId, @AuthenticationPrincipal User user) {

    // 서비스로직
    adminNoticeService.deleteNotice(noticeId, user);

    // 성공메시지
    String message = messageProvider.getMessage(NoticeSuccessCode.NOTICE_DELETED.getMessage());
    
    return ResponseEntity
              .status(NoticeSuccessCode.NOTICE_DELETED.getHttpStatus())
              .body(ApiResponse.success(
              NoticeSuccessCode.NOTICE_DELETED, 
              message, 
              noticeId));
  }

  // 공지사항 전체 목록 조회(페이징)
  @GetMapping()
  public ResponseEntity<?> allListNotices(
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size) {
    
    // 서비스로직
    Page<AdminNoticeListResDTO> resonseDTO = adminNoticeService.allListNotices(page, size);
    
    
    // 성공메시지
    String message = messageProvider.getMessage(NoticeSuccessCode.NOTICE_LIST_FETCHED.getMessage());

    return ResponseEntity
              .status(NoticeSuccessCode.NOTICE_LIST_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                NoticeSuccessCode.NOTICE_LIST_FETCHED, 
                message, 
                resonseDTO));
  }

  // 공지사항 키워드 검색 조회(페이징)
  @GetMapping("/search")
  public ResponseEntity<?> searchNotices(
    @RequestParam(name = "keyword") String keyword,
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size) {
    
    // 서비스로직
    Page<AdminNoticeSearchResDTO> responseDTO = adminNoticeService.searchNotices(keyword, page, size);

    // 성공메시지
    String message = messageProvider.getMessage(NoticeSuccessCode.NOTICE_FETCHED.getMessage());

    // 반환
    return ResponseEntity
              .status(NoticeSuccessCode.NOTICE_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                NoticeSuccessCode.NOTICE_FETCHED, 
                message, 
                responseDTO));
  }

  // 공지사항 상세
  @GetMapping("/{noticeId}")
  public ResponseEntity<?> detailNotice(@PathVariable("noticeId") Long noticeId) {

    // 서비스로직
    AdminNoticeDetailResDTO responseDTO = adminNoticeService.detailNotice(noticeId);

    // 성공메시지
    String message = messageProvider.getMessage(NoticeSuccessCode.NOTICE_FETCHED.getMessage());

    // 반환
    return ResponseEntity
              .status(NoticeSuccessCode.NOTICE_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                NoticeSuccessCode.NOTICE_DELETED, 
                message, 
                responseDTO));
  }
}
