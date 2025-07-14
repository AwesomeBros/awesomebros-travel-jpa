package com.tripgg.server.global.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tripgg.server.user.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

  private final Key accessTokenSigningKey;
  private final Key refreshTokenSigningKey;
  public final long accessTokenExpiredAt;
  public final long refreshTokenExpiredAt;

  public JwtProvider(
      @Value("${jwt.access-token-secret-key}") String accessTokenSecretKey,
      @Value("${jwt.refresh-token-secret-key}") String refreshTokenSecretKey,
      @Value("${jwt.access-token-expired-at}") long accessTokenExpiredAt,
      @Value("${jwt.refresh-token-expired-at}") long refreshTokenExpiredAt) {
    this.accessTokenSigningKey = Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes(StandardCharsets.UTF_8));
    this.refreshTokenSigningKey = Keys.hmacShaKeyFor(refreshTokenSecretKey.getBytes(StandardCharsets.UTF_8));
    this.accessTokenExpiredAt = accessTokenExpiredAt;
    this.refreshTokenExpiredAt = refreshTokenExpiredAt;
  }

  public String createAccessToken(String username, UUID id, Role role) {
    Instant now = Instant.now();
    Instant expiryInstant = now.plus(Duration.ofSeconds(accessTokenExpiredAt));
    Date expiredAt = Date.from(expiryInstant);
    String jwt = Jwts.builder().signWith(accessTokenSigningKey,
        SignatureAlgorithm.HS256).setSubject(username)
        .claim("id", id)
        .claim("role", role).setIssuedAt(new Date()).setExpiration(expiredAt).compact();
    LOGGER.info("Access Token 생성 완료");
    return jwt;
  }

  public String createRefreshToken(UUID id) {
    Instant now = Instant.now();
    Instant expiryInstant = now.plus(Duration.ofSeconds(refreshTokenExpiredAt));
    Date expiredAt = Date.from(expiryInstant);
    String jwt = Jwts.builder()
        .signWith(refreshTokenSigningKey, SignatureAlgorithm.HS256)
        .setIssuedAt(new Date())
        .claim("id", id)
        .setExpiration(expiredAt)
        .compact();
    LOGGER.info("Refresh Token 생성 완료");
    return jwt;
  }

  public Claims validateAccessToken(String accessToken) {
    Claims claims = null;
    try {
      claims = Jwts.parserBuilder()
          .setSigningKey(accessTokenSigningKey)
          .build()
          .parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException e) {
      LOGGER.warn("만료된 액세스 토큰입니다.", e);
      return null;
    } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException
        | SecurityException e) {
      LOGGER.warn("유효하지 않은 액세스 토큰입니다.", e);
      return null;
    }
    return claims;
  }

  public Claims validateRefreshToken(String refreshToken) {
    Claims claims = null;
    try {
      claims = Jwts.parserBuilder().setSigningKey(refreshTokenSigningKey).build().parseClaimsJws(refreshToken)
          .getBody();
    } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException
        | io.jsonwebtoken.security.SecurityException e) {
      LOGGER.warn("유효하지 않은 리프레시 토큰입니다.", e);
      return null;
    }
    return claims;
  }
}