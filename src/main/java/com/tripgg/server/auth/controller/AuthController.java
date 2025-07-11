package com.tripgg.server.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripgg.server.auth.request.LoginRequest;
import com.tripgg.server.auth.response.LoginResponse;
import com.tripgg.server.auth.response.TokenResponse;
import com.tripgg.server.auth.service.AuthService;
import com.tripgg.server.global.api.Api;
import com.tripgg.server.user.request.UserRequest;
import com.tripgg.server.user.response.UserResponse;
import com.tripgg.server.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;
  private final AuthService authService;

  Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

  @PostMapping("signup")
  public Api<UserResponse> signup(@Valid @RequestBody UserRequest request) {
    UserResponse response = userService.signup(request);
    return Api.OK(response);
  }

  @PostMapping("login")
  public Api<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    LoginResponse response = authService.login(request);
    return Api.OK(response);
  }

  @PostMapping("refresh")
  public Api<TokenResponse> refresh(@RequestHeader("Authorization") String authorization) {
    TokenResponse response = authService.refresh(authorization);
    return Api.OK(response);
  }
}
