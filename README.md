# 📚 도서관 관리 시스템 (React, SpringBoot, JPA) - 개발 중

> 도서 대여 시스템을 위한 React, Spring Boot 기반 서비스입니다.<br>
> 회원가입, 로그인, 도서 관리, 대출/반납, 리뷰, 공지사항, 관리자 기능을 포함합니다.<br>
> 개발하고 있는 상태이며 현재 백엔드 - 회원가입, 로그인, 도서 등록/수정까지 작업이 되었습니다.<br>
> 개발 기록은 [블로그-개발 기록/도서관 관리 시스템](https://dev-jelee.tistory.com/category/%EA%B0%9C%EB%B0%9C%20%EA%B8%B0%EB%A1%9D/%EB%8F%84%EC%84%9C%EA%B4%80%20%EA%B4%80%EB%A6%AC%20%EC%8B%9C%EC%8A%A4%ED%85%9C) 에
> 기록하고 있습니다.

---

## 🏗️ 기술 스택

- **Backend**: Java 17.0.12, Spring Boot 3.5.4
- **Build Tool**: Gradle
- **Database**: MySQL
- **ORM**: Spring Data JPA
- **Security**: Spring Security, JWT
- **Documentation**: Spring REST Docs, Postman
- **Deploy/Infra**: Docker, AWS (예정)

---

## 📁 프로젝트 구조 (backend)
```
com.jelee.librarymanagementsystem.backend
│
├── global # 전역 설정, 예외, 응답, 메시지 등
│ ├── config
│ ├── exception
│ ├── response
│ ├── jwt
│ ├── enums
│ └── util
│
├── domain
│ ├── auth # 인증/인가 관련
│ ├── user # 회원
│ ├── book # 도서
│ ├── loan # 대출
│ ├── review # 리뷰
│ ├── notice # 공지사항
│ └── admin # 관리자
```

---

## 📁 프로젝트 구조 (frontend)
- 구체적인 방향 아직 미정
```
com.jelee.librarymanagementsystem.frontend
│
├── assets
├── components
├── pages
├── styles
├── ...

```

---

## 🔐 인증 및 보안
- JWT 기반 인증/인가
- 로그인 시 토큰 발급
- 모든 요청은 토큰 기반 인증 필요 (`/api/v1/auth/**` 경로 제외)

---

## 💬 공통 응답 구조

```json
{
  "code": "USER_201",
  "message": "회원가입이 완료되었습니다.",
  "data": {
    // 응답 데이터
  }
}
```
| 필드        | 설명                                     |
| --------- | ------------------------------------------ |
| `code`    | 에러/성공 코드 (`USER_001`, `BOOK_404` 등) |
| `message` | 다국어 메시지 키 기반의 응답 메시지         |
| `data`    | 실제 응답 데이터                           |

---

## 📑 예외/성공 코드 관리 전략
- ErrorCode, SuccessCode는 각 도메인 별로 Enum으로 분리
- 공통 인터페이스 BaseErrorCode, BaseSuccessCode로 통합
- 메시지는 messages.properties에서 키로 관리 (다국어 확장 가능)

### ✅ ErrorCode 예시 (UserErrorCode.java)
| HttpStatus       | Code       | Message Key                     | 메시지              |
| ---------------- | ---------- | ------------------------------- | ---------------- |
| 409 CONFLICT     | `USER_001` | `error.user.username.duplicate` | 이미 사용 중인 아이디입니다. |
| 401 UNAUTHORIZED | `USER_401` | `error.user.invalid_password`   | 비밀번호가 일치하지 않습니다. |

### ✅ SuccessCode 예시 (BookSuccessCode.java)
| HttpStatus  | Code       | Message Key            | 메시지                |
| ----------- | ---------- | ---------------------- | ------------------ |
| 201 CREATED | `BOOK_201` | `success.book.created` | 도서가 성공적으로 등록되었습니다. |

### 📜 메시지 관리 예시 (messages.properties)
``` properties
# User
error.user.username.duplicate=이미 사용 중인 아이디입니다.
success.user.signup=회원가입이 완료되었습니다.

# Book
error.book.title.blank=도서 제목은 필수입니다.
success.book.created=도서가 성공적으로 등록되었습니다.
```

---

## 📌 향후 계획
- 각 도메인별 기능 모두 추가
- React로 프론트엔드 개발

---

## 👨‍💻 개발자 정보
- JiEun Lee
- Contact: waftyann@gmail.com
- 개발 블로그: https://dev-jelee.tistory.com/
