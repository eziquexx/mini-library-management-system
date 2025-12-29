# 📚 도서관 운영 웹 관리 시스템 (Backend 중심 개인 프로젝트)

Spring Boot와 JPA를 사용해 <br>
도서관 운영에 필요한 핵심 도메인(회원, 도서, 대출, 리뷰, 공지)을 <br>
백엔드 중심으로 설계·구현한 웹 관리 시스템입니다. <br><br>
단순 CRUD 구현을 넘어 <br>
인증/권한 분리, 트랜잭션 관리, 예외 처리, 배포 환경 구성까지 <br>
실제 서비스 흐름을 고려하며 개발했습니다.

 <br>

## 🚧 프로젝트 진행 현황
- 백엔드 API 설계 및 구현 완료 (Postman을 통한 테스트 진행)
- 사용자 페이지 UI 개발 및 테스트 완료
- Docker, Nginx 기반으로 AWS Lightsail에 배포
- 관리자 페이지 UI 개발 진행 중

 <br>
 
## 🛠 기술 스택
- Backend: Java 17, Spring Boot, Spring Security, JPA
- Frontend: React (User Page)
- Auth: JWT (HttpOnly Cookie)
- DB: MySQL
- Infra/Deploy: Docker, Nginx, AWS Lightsail
- CI/CD: GitHub Actions

 <br>

## 🧩 주요 기능
- 회원/관리자 권한 분리
- 도서 등록 및 대출/반납 관리
- 리뷰 작성 및 관리
- 공지사항 관리

 <br>

## 🏗️ 아키텍처 개요
- Layered Architecture 기반 (Controller → Service → Repository)
- JWT 인증 기반 요청 흐름

 <br>

## 🔍 개발 중 집중한 포인트
- 도메인 기준 패키지 구조 설계
- 공통 응답 / 에러 코드 구조화
- JPA 트랜잭션 경계(@Transactional) 이해 및 적용
- 배포 환경에서의 인증(Cookie, SameSite) 이슈 경험

 <br>

## 📖 상세 설계 및 트러블슈팅
프로젝트의 상세 설계, ERD, 트러블슈팅, 회고는
아래 Notion 문서에 정리되어 있습니다.

👉 [Notion 링크](https://dev-jelee.notion.site/2c3d0316bc68809081a2d361f8fd03e5?pvs=74)

 <br>

## 👨‍💻 개발자 정보
- JiEun Lee
- Contact: waftyann@gmail.com
- 개발 블로그: https://dev-jelee.tistory.com/
