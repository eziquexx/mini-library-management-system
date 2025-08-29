package com.jelee.librarymanagementsystem.global.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.enums.Role;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {
  
  private final JwtProperties jwtProperties;
  private Key key;

  public JwtTokenProvider(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }

  @PostConstruct
  public void init() {
    // 시크릿을 바이트 배열려 변환
    byte[] secretBytes = jwtProperties.getSecret().getBytes();
    this.key = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
  }

  // Token 생성
  // 사용자 id, role 정보 담기
  public String generateToken(User user) {

    Long userId = user.getId();
    String username = user.getUsername();
    Role role = user.getRole();

    Date now = new Date(); // 생성 시간
    Date expiry = new Date(now.getTime() + jwtProperties.getExpiration()); // 생성 시간 + 유효 1일

    return Jwts.builder()
              .setSubject(username)
              .claim("id", userId)
              .claim("role", role)
              .setIssuedAt(now)
              .setExpiration(expiry)
              .signWith(key, SignatureAlgorithm.HS256)
              .compact();
  }

  // 토큰 검증
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  // 토큰으로 사용자 정보 얻기
  public String getUsernameFromToken(String token) {
    return Jwts.parserBuilder()
              .setSigningKey(key).build()
              .parseClaimsJws(token)
              .getBody()
              .getSubject();
  }

  // 토큰으로 사용자 ID 얻기
  public Long getUserIdFromToken(String token) {
    String subject = Jwts.parserBuilder()
              .setSigningKey(key).build()
              .parseClaimsJws(token)
              .getBody()
              .getSubject();
    return Long.valueOf(subject);
  }

  // Cookie에서 토큰을 추출
  public String resolveTokenFromCookie(HttpServletRequest request) {
    if (request.getCookies() == null) return null;
    for (Cookie cookie : request.getCookies()) {
      if ("JWT".equals(cookie.getName())) {
        return cookie.getValue();
      }
    }

    return null;
  }

}
