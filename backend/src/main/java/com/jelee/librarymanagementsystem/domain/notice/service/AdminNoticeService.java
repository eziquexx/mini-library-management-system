package com.jelee.librarymanagementsystem.domain.notice.service;

import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeCreateResDTO;
import com.jelee.librarymanagementsystem.domain.notice.dto.admin.WriterDTO;
import com.jelee.librarymanagementsystem.domain.notice.entity.Notice;
import com.jelee.librarymanagementsystem.domain.notice.repository.NoticeRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
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
      throw new BaseException(NoticeErrorCode.NOTICE_TITLE_BLANK);
    }
    if (requestDTO.getContent() == null || requestDTO.getContent().trim().isEmpty()) {
      throw new BaseException(NoticeErrorCode.NOTICE_CONTENT_BLANK);
    }

    System.out.println(writer.getRole());

    // 권한 체크(ROLE_MANAGER, ROLE_ADMIN 만 가능)
    if (!(writer.getRole().equals(Role.ROLE_MANAGER) || writer.getRole().equals(Role.ROLE_ADMIN))) {
      throw new BaseException(NoticeErrorCode.NOTICE_CREATE_WRITER);
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
}
