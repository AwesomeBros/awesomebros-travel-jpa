package com.tripgg.server.auth.service;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tripgg.server.auth.request.LoginRequest;
import com.tripgg.server.auth.response.LoginResponse;
import com.tripgg.server.auth.response.TokenResponse;
import com.tripgg.server.global.error.ErrorCode;
import com.tripgg.server.global.exception.ApiException;
import com.tripgg.server.global.jwt.JwtProvider;
import com.tripgg.server.user.entity.User;
import com.tripgg.server.user.repository.UserRepository;
import com.tripgg.server.user.response.UserResponse;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

  @Override
  @Transactional
  public LoginResponse login(LoginRequest request) {
    String username = request.getUsername();
    String password = request.getPassword();
    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new ApiException(ErrorCode.FAILED_TO_LOGIN);
    }

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new ApiException(ErrorCode.FAILED_TO_LOGIN);
    }

    String accessToken = jwtProvider.createAccessToken(user.getUsername(), user.getId(), user.getRole());
    String refreshToken = jwtProvider.createRefreshToken(user.getId());
    long expiresIn = new Date().getTime() + jwtProvider.accessTokenExpiredAt * 1000;
    LoginResponse response = LoginResponse.builder()
        .user(UserResponse.from(user))
        .serverTokens(TokenResponse.from(accessToken, refreshToken, expiresIn))
        .build();
    return response;
  }

  @Override
  @Transactional
  public TokenResponse refresh(String authorization) {
    String refreshToken = parserRefreshToken(authorization);
    if (refreshToken == null) {
      throw new ApiException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
    Claims claims = jwtProvider.validateRefreshToken(refreshToken);
    if (claims == null) {
      throw new ApiException(ErrorCode.INVALID_REFRESH_TOKEN);
    }
    String userIdStr = claims.get("id", String.class);
    if (userIdStr == null) {
      LOGGER.warn("Refresh Token Claims에 사용자 ID가 없습니다.");
      throw new ApiException(ErrorCode.INVALID_REFRESH_TOKEN);
    }
    UUID userId = null;
    try {
      userId = UUID.fromString(userIdStr);
    } catch (IllegalArgumentException e) {
      LOGGER.warn("Refresh Token Claims의 사용자 ID 형식이 올바르지 않습니다. (UUID 형식 아님): {}", userIdStr, e);
      throw new ApiException(ErrorCode.INVALID_REFRESH_TOKEN);
    }
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    String newAccessToken = jwtProvider.createAccessToken(user.getUsername(), user.getId(), user.getRole());
    String newRefreshToken = jwtProvider.createRefreshToken(user.getId());
    long newExpiresIn = new Date().getTime() + jwtProvider.accessTokenExpiredAt * 1000;
    TokenResponse response = TokenResponse.builder()
        .accessToken(newAccessToken)
        .refreshToken(newRefreshToken)
        .expiresIn(newExpiresIn)
        .build();

    return response;
  }

  private String parserRefreshToken(String authorization) {
    final String REFRESH_PREFIX = "Refresh ";
    final int PREFIX_LENGTH = REFRESH_PREFIX.length();
    if (StringUtils.hasText(authorization) && authorization.startsWith(REFRESH_PREFIX)) {
      if (authorization.length() > PREFIX_LENGTH) {
        return authorization.substring(PREFIX_LENGTH);
      }
    }
    return null;
  }

}
