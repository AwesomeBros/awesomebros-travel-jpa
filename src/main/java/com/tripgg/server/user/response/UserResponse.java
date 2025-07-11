package com.tripgg.server.user.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.tripgg.server.user.entity.Role;
import com.tripgg.server.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

  private UUID id;
  private String nickname;
  private String username;
  private String url;
  private String email;
  private Role role;
  private String provider;
  private LocalDateTime createdAt;

  public static UserResponse from(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .nickname(user.getNickname())
        .username(user.getUsername())
        .email(user.getEmail())
        .url(user.getUrl())
        .role(user.getRole())
        .provider(user.getProvider())
        .createdAt(user.getCreatedAt())
        .build();
  }
}