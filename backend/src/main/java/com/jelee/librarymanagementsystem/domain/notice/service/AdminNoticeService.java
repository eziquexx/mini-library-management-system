package com.jelee.librarymanagementsystem.domain.notice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeCreateResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeDetailResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeListResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeSearchResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeUpdateResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.WriterDTO;
import com.jelee.librarymanagementsystem.domain.notice.entity.Notice;
import com.jelee.librarymanagementsystem.domain.notice.repository.NoticeRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.AuthErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.NoticeErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminNoticeService {
  
  private final NoticeRepository noticeRepository;
  private final UserRepository userRepository;

  // 공지사항 등록
  @Transactional
  public AdminNoticeCreateResDTO createNotice(AdminNoticeCreateReqDTO requestDTO, User user) {

    // 사용자 조회
    User writer = userRepository.findById(user.getId())
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

    // 필수 필드 Null 체크(title, content)
    if (requestDTO.getTitle() == null || requestDTO.getTitle().trim().isEmpty()) {
      throw new BaseException(NoticeErrorCode.NOTICE_TITLE_REQUIRED);
    }
    if (requestDTO.getContent() == null || requestDTO.getContent().trim().isEmpty()) {
      throw new BaseException(NoticeErrorCode.NOTICE_CONTENT_REQUIRED);
    }

    System.out.println(writer.getRole());

    // 권한 체크(ROLE_MANAGER, ROLE_ADMIN 만 가능)
    if (!(writer.getRole().equals(Role.ROLE_MANAGER) || writer.getRole().equals(Role.ROLE_ADMIN))) {
      throw new BaseException(NoticeErrorCode.NOTICE_CREATE_AUTHORIZED);
    }

    // Notice 객체 생성
    Notice notice = Notice.builder()
                      .title(requestDTO.getTitle())
                      .content(requestDTO.getTitle())
                      .writer(writer)
                      .build();

    // notice DB에 저장
    noticeRepository.save(notice);

    AdminNoticeCreateResDTO responseDTO = AdminNoticeCreateResDTO.builder()
        .id(notice.getId())
        .title(notice.getTitle())
        .createdDate(notice.getCreatedDate())
        .writer(new WriterDTO(writer))
        .build();
        
    return responseDTO;
  }

  // 공지사항 수정
  @Transactional
  public AdminNoticeUpdateResDTO updateNotice(Long noticeId, AdminNoticeUpdateReqDTO requestDTO, User user) {

    // noticeId로 공지사항 데이터 가져오기
    Notice notice = noticeRepository.findById(noticeId)
        .orElseThrow(() -> new BaseException(NoticeErrorCode.NOTICE_NOT_FOUND));

    // user 권한 체크
    Role userRole = user.getRole();
    if (!(userRole.equals(Role.ROLE_MANAGER) || userRole.equals(Role.ROLE_ADMIN))) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 필수 필드 Null 체크(title, content)
    if (requestDTO.getTitle() == null || requestDTO.getTitle().trim().isEmpty()) {
      throw new BaseException(NoticeErrorCode.NOTICE_TITLE_REQUIRED);
    }
    if (requestDTO.getContent() == null || requestDTO.getContent().trim().isEmpty()) {
      throw new BaseException(NoticeErrorCode.NOTICE_CONTENT_REQUIRED);
    }

    // 내용 업데이트(title, content, writer, updatedAt)
    notice.update(requestDTO, user);
    
    // 반환
    return new AdminNoticeUpdateResDTO(notice);
  }

  // 공지사항 삭제
  @Transactional
  public void deleteNotice(Long noticeId, User user) {

    // notice 조회
    Notice notice = noticeRepository.findById(noticeId)
        .orElseThrow(() -> new BaseException(NoticeErrorCode.NOTICE_NOT_FOUND));

    // 권한 체크
    Role userRole = user.getRole();
    if (!(userRole.equals(Role.ROLE_MANAGER) || userRole.equals(Role.ROLE_ADMIN))) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }
    
    // 삭제
    noticeRepository.delete(notice);
  }

  // 공지사항 전체 목록 조회
  @Transactional
  public Page<AdminNoticeListResDTO> allListNotices(int page, int size) {
    
    // 페이징 정보 생성
    Pageable pageable = PageRequest.of(page, size);

    // 결과를 Page<Notice> 타입으로 저장
    Page<Notice> result = noticeRepository.findAll(pageable);

    // 엔티티 리스트를 DTO 리스트로 변환
    List<AdminNoticeListResDTO> dtoList = result.getContent()
        .stream()
        .map(AdminNoticeListResDTO::new)
        .toList();
    
    // 반환할때 DTO 리스트를 Page 형식으로 래핑하여 반환
    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  }

  // 공지사항 검색
  @Transactional
  public Page<AdminNoticeSearchResDTO> searchNotices(String keyword, int page, int size) {

    // 페이징 정보 생성
    Pageable pageable = PageRequest.of(page, size);
    
    // 결과를 Page<Notice> 타입으로 저장
    Page<Notice> result = noticeRepository.findByTitleContainingIgnoreCase(keyword, pageable);

    // 결과 없는 경우 예외
    if (result.isEmpty()) {
      throw new BaseException(NoticeErrorCode.NOTICE_NOT_FOUND);
    }

    // 엔티티 리스트를 DTO 리스트로 변환
    List<AdminNoticeSearchResDTO> dtoList = result.getContent()
          .stream()
          .map(AdminNoticeSearchResDTO::new)
          .toList();

    // 반환할때 DTO 리스트를 Page 형식으로 래핑하여 반환
    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  }

  // 공지사항 상세보기
  public AdminNoticeDetailResDTO detailNotice(Long noticeId) {
    
    // noticeId로 조회후 Notice 객체 생성
    Notice notice = noticeRepository.findById(noticeId)
        .orElseThrow(() -> new BaseException(NoticeErrorCode.NOTICE_NOT_FOUND));

    // notice 반환
    return new AdminNoticeDetailResDTO(notice);
  }
}
