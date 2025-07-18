package com.tripgg.server.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshRequest {
  @NotBlank(message = "리프레시 토큰은 필수입니다.")
  private String refreshToken;
}
