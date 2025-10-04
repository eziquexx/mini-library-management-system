package com.jelee.librarymanagementsystem.domain.loan.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;
import com.jelee.librarymanagementsystem.domain.book.enums.BookStatus;
import com.jelee.librarymanagementsystem.domain.book.repository.BookRepository;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanCreateResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanDetailResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanExtendedResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanListResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanLostResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanReturnResDTO;
import com.jelee.librarymanagementsystem.domain.loan.dto.admin.AdminLoanSearchResDTO;
import com.jelee.librarymanagementsystem.domain.loan.entity.Loan;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanSearchType;
import com.jelee.librarymanagementsystem.domain.loan.enums.LoanStatus;
import com.jelee.librarymanagementsystem.domain.loan.repository.LoanRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.enums.UserStatus;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.AuthErrorCode;
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

  /*
   * 관리자: 도서 대출 등록
   */
  @Transactional
  public AdminLoanCreateResDTO createLoan(AdminLoanCreateReqDTO requestDTO, Long adminUserId) {

    // 관리자 권환 조회 및 예외 처리
    User userAdmin = userRepository.findById(adminUserId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (userAdmin.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 도서 유효성 검사
    Book book = bookRepository.findById(requestDTO.getBookId())
        .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));

    // 사용자 유효성 검사 (도서 대출자)
    User user = userRepository.findById(requestDTO.getUserId())
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    // 도서 대출 가능 여부 확인
    if (book.getStatus() != BookStatus.AVAILABLE) {
      throw new BaseException(LoanErrorCode.LOAN_ALREADY_BORROWED);
    }

    // 사용자의 상태가 ACTIVE만 대출 가능
    if (user.getStatus() != UserStatus.ACTIVE) {
      throw new BaseException(LoanErrorCode.LOAN_USER_INELIGIBLE);
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

    // 반환
    return new AdminLoanCreateResDTO(loan);
  }

  /*
   * 관리자: 도서 대출 전체 목록 조회 (페이징)
   */
  public PageResponse<AdminLoanListResDTO> allListLoans(LoanStatus status, int page, int size, Long userId) {

    // 관리자 권환 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // status에 값이 없으면 findAll, 값이 있으면 해당 status로 조회
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

    // Page<Loan>을 AdminLoanListResDTO로 맵핑하여 생성
    Page<AdminLoanListResDTO> pageDTO = result.map(AdminLoanListResDTO::new);

    // 반환
    return new PageResponse<>(pageDTO);
  }

  /*
   * 관리자: 도서 대출 상세 조회
   */
  public AdminLoanDetailResDTO detailLoan(Long loanId, Long userId) {

    // 관리자 권환 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 도서 대출 조회 및 예외 처리
    Loan loan = loanRepository.findById(loanId)
        .orElseThrow(() -> new BaseException(LoanErrorCode.LOAN_NOT_FOUND));
    
    // 반환
    return new AdminLoanDetailResDTO(loan);
  }

  /*
   * 관리자: 도서 대출 타입별 검색
   */
  public PageResponse<AdminLoanSearchResDTO> searchLoan(LoanSearchType type, String keyword, LoanStatus status, int page, int size, Long adminUserId) {

    // 관리자 권환 조회 및 예외 처리
    User userAdmin = userRepository.findById(adminUserId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (userAdmin.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);
    
    // type별 검색 - BOOKTITLE, USERID
    Page<Loan> result;
    switch (type) {
      case BOOKTITLE:
        // 도서 유효검사
        Book book = bookRepository.findByTitleContainingIgnoreCase(keyword)
            .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));
        
        // 도서 대출 상태가 null이면 도서명으로만 검색
        // 도서 대출 상태가 null이 아니면, 도서명 + 대출 상태로 검색
        result = (status != null)
            ? loanRepository.findByBook_TitleContainingIgnoreCaseAndStatus(book.getTitle(), status, pageable)
            : loanRepository.findByBook_TitleContainingIgnoreCase(book.getTitle(), pageable);
        break;
      
      case USERNAME:
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
        throw new BaseException(LoanErrorCode.LOAN_SEARCH_TYPE_INVALID);
    }

    // Page<Loan> -> Page<AdminLoanSearchResDTO>로 맵핑
    Page<AdminLoanSearchResDTO> pageDTO = result.map(AdminLoanSearchResDTO::new);

    // 반환
    return new PageResponse<>(pageDTO);
  }

  /*
   * 관리자: 도서 대출 반납 처리
   */
  @Transactional
  public AdminLoanReturnResDTO returnLoan(Long loanId, Long userId) {

    // 관리자 권환 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // Loan 엔티티 조회 + 예외 처리
    Loan loan = loanRepository.findById(loanId)
        .orElseThrow(() -> new BaseException(LoanErrorCode.LOAN_NOT_FOUND));
    
    // Book 엔티티 조회
    Book book = bookRepository.findById(loan.getBook().getId())
        .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));

    // loan 상태 체크 (반납이 된 상태인지)
    if (loan.getStatus() == LoanStatus.RETURNED) {
      throw new BaseException(LoanErrorCode.LOAN_ALREADY_RETURNED);
    }

    // 대출 내역 반납 시간과 상태 변경
    loan.setReturnDate(LocalDateTime.now());
    loan.setStatus(LoanStatus.RETURNED);

    // 도서 상태와 수정 시간 변경
    book.setStatus(BookStatus.AVAILABLE);
    book.setUpdatedAt(LocalDateTime.now());

    // 변경된 loan 반환
    return new AdminLoanReturnResDTO(loan);
  }

  /*
   * 관리자: 도서 대출 연장 처리
   */
  @Transactional
  public AdminLoanExtendedResDTO extendLoan(Long loanId, Long userId) {
    
    // 관리자 권환 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // loanId로 조회 및 예외처리
    Loan loan = loanRepository.findById(loanId)
        .orElseThrow(() -> new BaseException(LoanErrorCode.LOAN_NOT_FOUND));
    
    // loan 상태가 대출중인 것만 가능
    // 연체, 반납, 분실 상태는 불가
    if (loan.getStatus() != LoanStatus.LOANED) {
      throw new BaseException(LoanErrorCode.LOAN_CANNOT_EXTEND);
    }

    // loan 대출 연장 여부 체크 및 예외처리
    if (loan.isExtended() != false) {
      throw new BaseException(LoanErrorCode.LOAN_ALREADY_EXTENDED);
    }

    // 대출 연장 및 반납 기간 업데이트
    // 대출 연장은 1회 가능, 7일 추가
    loan.setExtended(true);
    loan.setDueDate(loan.getDueDate().plusDays(7));

    // 반환
    return new AdminLoanExtendedResDTO(loan);
  }

  /*
   * 관리자: 도서 분실 처리
   */
  @Transactional
  public AdminLoanLostResDTO loanLostBook(Long loanId, Long userId) {

    // 관리자 권환 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }
    
    // loanId 조회 + 예외 처리
    Loan loan = loanRepository.findById(loanId)
        .orElseThrow(() -> new BaseException(LoanErrorCode.LOAN_NOT_FOUND));

    // bookId 조회 + 예외 처리
    Book book = bookRepository.findById(loan.getBook().getId())
        .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));
    
    // Loan 엔티티 LOST 상태 여부 체크 + 예외 처리
    if (loan.getStatus() == LoanStatus.LOST) {
      throw new BaseException(LoanErrorCode.LOAN_ALREADY_LOST);
    }

    // Loan 상태가 LOANED, OVERDUE만 LOST 처리 가능
    LoanStatus current = loan.getStatus();
    if (current != LoanStatus.LOANED && current != LoanStatus.OVERDUE) {
      throw new BaseException(LoanErrorCode.LOAN_STATUS_INVALID_FOR_LOST);
    }
    
    // Book 엔티티 상태 체크 후 분실 처리
    if (book.getStatus() == BookStatus.LOST) {
      throw new BaseException(BookErrorCode.BOOK_ALREADY_LOST);
    }

    // 도서 대출 내역과 도서 상태 분실 처리
    loan.setStatus(LoanStatus.LOST);
    loan.setLostDate(LocalDateTime.now());
    book.setStatus(BookStatus.LOST);
    book.setLostedAt(LocalDateTime.now());
  
    // 응답
    return new AdminLoanLostResDTO(loan);
  }
}
