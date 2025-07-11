package com.tripgg.server.user.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tripgg.server.global.error.ErrorCode;
import com.tripgg.server.global.exception.ApiException;
import com.tripgg.server.user.entity.User;
import com.tripgg.server.user.repository.UserRepository;
import com.tripgg.server.user.request.UserRequest;
import com.tripgg.server.user.response.UserResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  @Override
  public UserResponse signup(UserRequest request) {
    Boolean isExistsUsername = userRepository.existsByUsername(request.getUsername());
    if (isExistsUsername) {
      throw new ApiException(ErrorCode.DUPLICATED_USERNAME);
    }

    Boolean isExistsEmail = userRepository.existsByEmail(request.getEmail());
    if (isExistsEmail) {
      throw new ApiException(ErrorCode.DUPLICATED_EMAIL);
    }

    String encodedPassword = passwordEncoder.encode(request.getPassword());

    User newUser = User.builder()
        .nickname(request.getNickname())
        .username(request.getUsername())
        .password(encodedPassword)
        .build();

    User savedUser = userRepository.save(newUser);
    UserResponse response = UserResponse.from(savedUser);
    return response;
  }

  @Override
  public UserResponse getMe(UUID id) {
    User user = userRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    UserResponse response = UserResponse.from(user);
    return response;
  }

  @Override
  public List<UserResponse> getAllUsers() {
    List<User> userList = userRepository.findAll();
    if (userList.isEmpty()) {
      throw new ApiException(ErrorCode.USER_NOT_FOUND);
    }
    return userList.stream()
        .map(UserResponse::from)
        .toList();
  }
}
