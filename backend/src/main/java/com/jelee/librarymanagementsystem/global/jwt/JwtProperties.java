package com.jelee.librarymanagementsystem.global.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
  
  private String secret;
  private long expiration;
  private String issuer;

  public String getSecret() {
    return secret;
  }
  public long getExpiration() {
    return expiration;
  }
  public String getIssuer() {
    return issuer;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }
  public void setExpiration(long expiration) {
    this.expiration = expiration;
  }
  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }
}
