package com.jelee.librarymanagementsystem.global.jwt;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  
  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {

    // 로그인, 회원가입 요청은 필터 무시
    String path = request.getRequestURI();
    if (path.startsWith("/api/v1/auth")) {
      filterChain.doFilter(request, response); 
      return;
    }
    
    // 1. 쿠키에서 Jwt 찾기
    String token = null;
    if (request.getCookies() != null) {
      token = Arrays.stream(request.getCookies())
                  .filter(c -> c.getName().equals("JWT"))
                  .findFirst()
                  .map(Cookie::getValue)
                  .orElse(null);
    }

    // 2. Jwt 유효성 검사
    if (token != null && jwtTokenProvider.validateToken(token)) {
      String username = jwtTokenProvider.getUsernameFromToken(token);

      // 3. 사용자 정보 조회
      User user = userRepository.findByUsername(username).orElse(null);
      if (user != null) {
        // 4. 인증 객체 생성 및 등록
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    // 5. 다음 필터로 이동
    filterChain.doFilter(request, response);
  }
}
