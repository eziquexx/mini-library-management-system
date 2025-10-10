package com.jelee.librarymanagementsystem.domain.notice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.notice.dto.client.UserNoticeDetailResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.client.UserNoticeListResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.client.UserNoticeSearchResDTO;
import com.jelee.librarymanagementsystem.domain.notice.entity.Notice;
import com.jelee.librarymanagementsystem.domain.notice.repository.NoticeRepository;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.NoticeErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
  
  private final NoticeRepository noticeRepository;

  /*
   * 공용: 공지사항 전체 목록 보기 (페이징)
   */
  public PageResponse<UserNoticeListResDTO> allListNotices(int page, int size) {

    // Pageable. 페이징 준비
    Pageable pageable = PageRequest.of(page, size);

    // Page<Notice> 객체 생성
    Page<Notice> result = noticeRepository.findAll(pageable);

    // 데이터 없을 시 예외처리
    if (result.isEmpty()) {
      throw new BaseException(NoticeErrorCode.NOTICE_NOT_FOUND);
    }

    // Page<Notice> -> Page<UserNoticeListResDTO>로 맵핑 
    Page<UserNoticeListResDTO> pageList = result.map(UserNoticeListResDTO::new);

    // 반환
    return new PageResponse<>(pageList);
  }

  // 공지사항 상세보기
  public UserNoticeDetailResDTO detailNotice(Long noticeId) {
    
    // noticeId로 해당 데이터 조회
    Notice notice = noticeRepository.findById(noticeId)
        .orElseThrow(() -> new BaseException(NoticeErrorCode.NOTICE_NOT_FOUND));
    
    // Notice 반환
    return new UserNoticeDetailResDTO(notice);
  }

  // 공지사항 검색 목록 보기 (페이징)
  public Page<UserNoticeSearchResDTO> searchNotices(String keyword, int page, int size) {

    // 페이징 준비
    Pageable pageable = PageRequest.of(page, size);

    // 레포지토리 로직
    // 결과를 Page<Notice> 타입으로 저장
    Page<Notice> result = noticeRepository.findByTitleContainingIgnoreCase(keyword, pageable);

    // 결과 없을 시 예외처리
    if (result.isEmpty()) {
      throw new BaseException(NoticeErrorCode.NOTICE_NOT_FOUND);
    }

    // 엔티티 리스트를 DTO 리스트로 변환
    List<UserNoticeSearchResDTO> dtoList = result.getContent()
        .stream()
        .map(UserNoticeSearchResDTO::new)
        .toList();

    // 반환할 때 DTO 리스트를 Page 형식으로 랩핑하여 반환
    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  } 
}
