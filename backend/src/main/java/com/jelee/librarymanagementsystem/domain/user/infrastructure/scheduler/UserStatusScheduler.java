package com.jelee.librarymanagementsystem.domain.user.infrastructure.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStatusScheduler {
  
  private final UserRepository userRepository;

  // 매일 새벽 3시에 실행 (corn 표현식: 초 분 시 일 월 요일)
  @Scheduled(cron = "0 0 3 * * *")
  @Transactional
  public void updateInactiveUsersToDeleted() {
    log.info("UserStatusScheduler started - update");

    LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);

    // INACTIVE 상태면서 inactivveAt이 cutoffDate 이전이 유저 찾기
    var usersToDelete = userRepository.findByStatusAndInactiveAtBefore(UserStatus.INACTIVE, cutoffDate);

    for (User user : usersToDelete) {
      user.setStatus(UserStatus.DELETED);
      user.setDeletedAt(LocalDateTime.now());
      userRepository.save(user);
      log.info("User id={} status updated to DELETED", user.getId());
    }

    log.info("UserStatusScheduler finished");
  }
}
