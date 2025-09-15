package com.jelee.librarymanagementsystem.domain.loan.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.loan.dto.user.UserLoanListResDTO;
import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.repository.LoanRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserLoanService {
  
  private final LoanRepository loanRepository;
  private final UserRepository userRepository;

  // 사용자(본인) 도서 대출 내역 조회 (페이징)
  public Page<UserLoanListResDTO> userLoanList(int page, int size, User user) {

    // 사용자 조회(유효 검사)
    User userFromDb = userRepository.findById(user.getId())
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    Long userId = userFromDb.getId();

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // DB에서 사용자id로 조회
    Page<Loan> pageResultDTO = loanRepository.findByUser_Id(userId, pageable);

    // pageDTO를 List형태로 변환
    List<UserLoanListResDTO> listDTO = pageResultDTO.getContent()
        .stream()
        .map(UserLoanListResDTO::new)
        .toList();

    // listDTO를 PageImpl로 랩핑하여 반환
    return new PageImpl<>(listDTO, pageResultDTO.getPageable(), pageResultDTO.getTotalElements());
  }
}
