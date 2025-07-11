package com.tripgg.server.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

  @NotBlank(message = "이름은 필수 입력 항목입니다.")
  private String nickname;

  @NotBlank(message = "사용자 아이디는 필수 입력 항목입니다.")
  private String username;

  @NotBlank(message = "이메일은 필수 입력 항목입니다.")
  @Email(message = "유효하지 않은 이메일 형식입니다.")
  private String email;

  @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
  private String password;

  private String url;

  private String provider;
}
