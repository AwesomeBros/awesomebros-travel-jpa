package com.tripgg.server.global.jwt;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtPayload {
  private UUID id;
  private String email;
  private String role;
}
