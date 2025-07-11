package com.tripgg.server.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

  @NotBlank(message = "사용자 아이디는 필수 입력값입니다.")
  private String username;

  @NotBlank(message = "비밀번호는 필수 입력값입니다.")
  private String password;
}
