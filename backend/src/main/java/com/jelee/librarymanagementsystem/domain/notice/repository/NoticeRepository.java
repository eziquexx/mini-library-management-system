package com.jelee.librarymanagementsystem.domain.notice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jelee.librarymanagementsystem.domain.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
  Page<Notice> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
