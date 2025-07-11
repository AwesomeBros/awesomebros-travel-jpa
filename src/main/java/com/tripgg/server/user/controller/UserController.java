package com.tripgg.server.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripgg.server.global.annotation.CurrentUser;
import com.tripgg.server.global.api.Api;
import com.tripgg.server.global.jwt.JwtPayload;
import com.tripgg.server.user.response.UserResponse;
import com.tripgg.server.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

  private final UserService userService;

  @GetMapping("me")
  public Api<UserResponse> getMe(@CurrentUser JwtPayload user) {
    UserResponse response = userService.getMe(user.getId());
    return Api.OK(response);
  }

  @GetMapping("all")
  public Api<List<UserResponse>> getAllUsers() {
    List<UserResponse> response = userService.getAllUsers();
    return Api.OK(response);
  }
}
