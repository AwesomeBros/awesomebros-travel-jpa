package com.tripgg.server.user.service;

import java.util.List;
import java.util.UUID;

import com.tripgg.server.user.request.UserRequest;
import com.tripgg.server.user.response.UserResponse;

public interface UserService {

  UserResponse signup(UserRequest request);

  UserResponse getMe(UUID id);

  List<UserResponse> getAllUsers();

}
