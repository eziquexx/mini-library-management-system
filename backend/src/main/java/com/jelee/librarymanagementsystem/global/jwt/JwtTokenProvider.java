package com.jelee.librarymanagementsystem.global.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

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
  public String generateToken(String username, String role) {
    Date now = new Date(); // 생성 시간
    Date expiry = new Date(now.getTime() + jwtProperties.getExpiration()); // 생성 시간 + 유효 1일

    return Jwts.builder()
              .setSubject(username)
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
}
