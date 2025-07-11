package com.tripgg.server.global.jwt;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    try {
      String accessToken = parseBearerToken(request);

      if (accessToken == null) {
        filterChain.doFilter(request, response);
        return;
      }

      Claims claims = jwtProvider.validateAccessToken(accessToken);

      if (claims == null) {
        LOGGER.debug("유효하지 않은 Access Token입니다. (JWT Filter)");
        filterChain.doFilter(request, response);
        return;
      }

      String email = claims.getSubject();
      String userIdString = claims.get("id", String.class);

      String role = claims.get("role", String.class);

      UUID userId = null;
      if (userIdString != null && !userIdString.isEmpty()) {
        try {
          userId = UUID.fromString(userIdString);
        } catch (IllegalArgumentException e) {
          LOGGER.warn("Access Token Claims의 사용자 ID 형식이 올바르지 않습니다. (UUID 형식 아님): {}", userIdString, e);
          filterChain.doFilter(request, response);
          return;
        }
      } else {
        LOGGER.warn("Access Token Claims에 사용자 ID가 없거나 비어있습니다.");
        filterChain.doFilter(request, response);
        return;
      }

      AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          new JwtPayload(userId, email, role),
          null,
          AuthorityUtils.createAuthorityList(role));
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
      securityContext.setAuthentication(authenticationToken);

      SecurityContextHolder.setContext(securityContext);

      LOGGER.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", email, request.getRequestURI());

    } catch (ExpiredJwtException e) {
      LOGGER.warn("액세스 토큰이 만료되었습니다. (JWT Filter)", e);
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      throw e;

    } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      LOGGER.warn("토큰이 유효하지 않습니다. (JWT Filter)", e);

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      throw e;

    } catch (Exception e) {
      LOGGER.error("알수 없는 오류가 발생했습니다. (JWT Filter)", e);
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      throw e;
    }
    filterChain.doFilter(request, response);
  }

  private String parseBearerToken(HttpServletRequest request) {
    String authorization = request.getHeader("Authorization");
    boolean hasAuthorization = StringUtils.hasText(authorization);
    if (!hasAuthorization) {
      LOGGER.debug("Authorization 헤더가 없습니다.");
      return null;
    }

    if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
      return authorization.substring(7);
    }
    return null;
  }
}
