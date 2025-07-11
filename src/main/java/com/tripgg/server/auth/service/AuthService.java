package com.tripgg.server.auth.service;

import com.tripgg.server.auth.request.LoginRequest;
import com.tripgg.server.auth.response.LoginResponse;
import com.tripgg.server.auth.response.TokenResponse;

public interface AuthService {

  LoginResponse login(LoginRequest request);

  TokenResponse refresh(String authorization);
}
