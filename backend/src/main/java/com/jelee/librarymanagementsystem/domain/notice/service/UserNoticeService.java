package com.jelee.librarymanagementsystem.domain.notice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.notice.dto.client.UserNoticeDetailResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.client.UserNoticeListResDTO;
import com.jelee.librarymanagementsystem.domain.notice.entity.Notice;
import com.jelee.librarymanagementsystem.domain.notice.repository.NoticeRepository;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.NoticeErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserNoticeService {
  
  private final NoticeRepository noticeRepository;

  // 공지사항 전체 목록 조회
  public Page<UserNoticeListResDTO> allListNotices(int page, int size) {

    // Pageable. 페이징 준비
    Pageable pageable = PageRequest.of(page, size);

    // Page<Notice> 객체 생성
    Page<Notice> result = noticeRepository.findAll(pageable);

    // 데이터 없을 시 예외처리
    if (result.isEmpty()) {
      throw new BaseException(NoticeErrorCode.NOTICE_NOT_FOUND);
    }

    // Page를 List타입으로 변환
    List<UserNoticeListResDTO> dtoList = result.getContent()
        .stream()
        .map(UserNoticeListResDTO::new)
        .toList();

    // dtoList를 PageImpl로 랩핑하여 반환
    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  }

  // 공지사항 상세보기
  public UserNoticeDetailResDTO detailNotice(Long noticeId) {
    
    // noticeId로 해당 데이터 조회
    Notice notice = noticeRepository.findById(noticeId)
        .orElseThrow(() -> new BaseException(NoticeErrorCode.NOTICE_NOT_FOUND));
    
    // Notice 반환
    return new UserNoticeDetailResDTO(notice);
  }
}
