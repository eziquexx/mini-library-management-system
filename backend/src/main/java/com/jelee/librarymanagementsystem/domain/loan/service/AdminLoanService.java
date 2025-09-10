package com.jelee.librarymanagementsystem.domain.loan.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;
import com.jelee.librarymanagementsystem.domain.book.enums.BookStatus;
import com.jelee.librarymanagementsystem.domain.book.repository.BookRepository;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanCreateResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanDetailResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanListResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanReturnResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanSearchResDTO;
import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanSearchType;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;
import com.jelee.librarymanagementsystem.domain.loan.repository.LoanRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.BookErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.LoanErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminLoanService {
  
  private final LoanRepository loanRepository;
  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  // 도서 대출 등록
  @Transactional
  public AdminLoanCreateResDTO createLoan(AdminLoanCreateReqDTO requestDTO) {

    // 도서 유효성 검사
    Book book = bookRepository.findById(requestDTO.getBookId())
        .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));

    // 사용자 유효성 검사
    User user = userRepository.findById(requestDTO.getUserId())
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    // 도서 대출 가능 여부 확인
    if (book.getStatus() != BookStatus.AVAILABLE) {
      throw new BaseException(LoanErrorCode.LOAN_ALREADY_BORROWED);
    }

    // 사용자의 상태가 ACTIVE만 대출 가능
    if (user.getStatus() != UserStatus.ACTIVE) {
      throw new BaseException(LoanErrorCode.USER_NOT_ELIGIBLE_FOR_LOAN);
    }

    // 사용자 대출 3건이상은 대출 불가
    int loanedCount = loanRepository.countByUserAndStatus(user, LoanStatus.LOANED);
    if (loanedCount >= 3) {
      throw new BaseException(LoanErrorCode.LOAN_LIMIT_EXCEEDED);
    }
    
    // 대출 생성 & 저장
    Loan loan = Loan.builder()
                  .user(user)
                  .book(book)
                  .build();

    loanRepository.save(loan);

    // 도서 상태 변경
    book.setStatus(BookStatus.BORROWED);

    // 응답
    return new AdminLoanCreateResDTO(loan);
  }

  // 전체 대출 목록 조회
  @Transactional
  public Page<AdminLoanListResDTO> allListLoans(LoanStatus status, int page, int size) {

    // 페이징 준비
    Pageable pageable = PageRequest.of(page, size);

    // status에 값이 없으면 findAll, 값이 있으면 findByStatus
    // 결과를 Page<Loan> 타입으로 저장
    Page<Loan> result;
    if (status != null) {
      result = loanRepository.findByStatus(status, pageable); 
    } else {
      result = loanRepository.findAll(pageable);
    }

    // result에 값이 없으면 예외처리
    if (result.isEmpty()) {
      throw new BaseException(LoanErrorCode.LOAN_NOT_FOUND);
    }

    // Page타입의 결과를 List로 변환
    List<AdminLoanListResDTO> dtoList = result.getContent()
        .stream()
        .map(AdminLoanListResDTO::new)
        .toList();

    // 반환시 DTO 리스트를 Page 형식으로 랩핑하여 반환
    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  }

  // 도서 대출 상세 조회
  @Transactional
  public AdminLoanDetailResDTO detailLoan(Long loanId) {

    // loan 유효 검사
    Loan loan = loanRepository.findById(loanId)
        .orElseThrow(() -> new BaseException(LoanErrorCode.LOAN_NOT_FOUND));
    
    // loan 데이터를 가지고 응답 DTO 객체 생성하여 반환
    return new AdminLoanDetailResDTO(loan);
  }

  // 도서 대출 조건별 검색
  @Transactional
  public Page<AdminLoanSearchResDTO> searchLoan(LoanSearchType typeStr, String keyword, LoanStatus status, int page, int size) {

    // 값 체크
    System.out.println(typeStr);
    System.out.println(keyword);
    System.out.println(status);

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // type 예외처리
    LoanSearchType type;
    try {
      type = LoanSearchType.valueOf(typeStr.toString().toUpperCase());
    } catch(IllegalArgumentException e) {
      throw new BaseException(LoanErrorCode.LOAN_SEARCH_TYPE_FAILED);
    }
    
    // type별 검색 - BOOKTITLE, USERID
    Page<Loan> result;
    
    switch (type) {
      case BOOKTITLE:
        // 도서 유효검사
        Book book = bookRepository.findByTitle(keyword)
            .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));
        
        // 도서 대출 상태가 null이면 도서명으로만 검색
        // 도서 대출 상태가 null이 아니면, 도서명 + 대출 상태로 검색
        result = (status != null)
            ? loanRepository.findByBook_TitleContainingIgnoreCaseAndStatus(book.getTitle(), status, pageable)
            : loanRepository.findByBook_TitleContainingIgnoreCase(book.getTitle(), pageable);
        break;
      
      case USERID:
        // 사용자 유효검사
        User user = userRepository.findByUsername(keyword)
            .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
            
        // 도서 대출 상태가 null이면 사용자명으로만 검색
        // 도서 대출 상태가 null이 아니면 사옹자명 + 대출 상태로 검색
        result = (status != null)
            ? loanRepository.findByUser_UsernameContainingIgnoreCaseAndStatus(user.getUsername(), status, pageable)
            : loanRepository.findByUser_UsernameContainingIgnoreCase(user.getUsername(), pageable);
        break;

      default:
        throw new BaseException(LoanErrorCode.LOAN_SEARCH_TYPE_FAILED);
    }

    // Page dto를 List 형태로 변환
    List<AdminLoanSearchResDTO> dtoList = result.getContent()
        .stream()
        .map(AdminLoanSearchResDTO::new)
        .toList();

    // 반환시 dtoList를 PageImpl로 감싸서 반환
    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  }

  //도서 반납 처리
  @Transactional
  public AdminLoanReturnResDTO returnLoan(Long loanId) {

    // Loan 엔티티 조회 + 예외 처리
    Loan loan = loanRepository.findById(loanId)
        .orElseThrow(() -> new BaseException(LoanErrorCode.LOAN_NOT_FOUND));

    if (loan.getStatus() == LoanStatus.RETURNED) {
      throw new BaseException(LoanErrorCode.LOAN_ALREADY_RETURNED);
    }

    // 반납 시간과 상태 변경
    loan.setReturnDate(LocalDateTime.now());
    loan.setStatus(LoanStatus.RETURNED);

    // 변경된 loan 반환
    return new AdminLoanReturnResDTO(loan);
  }
}
