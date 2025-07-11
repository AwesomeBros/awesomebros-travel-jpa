package com.tripgg.server.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private String nickname;

  private String username;

  private String email;

  private String password;

  private String url;

  @Enumerated(EnumType.STRING)
  @ColumnDefault("'USER'")
  private Role role;

  @ColumnDefault("'일반'")
  private String provider;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
