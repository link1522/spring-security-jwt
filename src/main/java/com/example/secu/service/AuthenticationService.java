package com.example.secu.service;

import com.example.secu.dto.request.LoginRequest;
import com.example.secu.dto.request.RefreshTokenRequest;
import com.example.secu.dto.request.SignupRequest;
import com.example.secu.dto.response.JwtAuthenticationResponse;
import com.example.secu.entity.User;

public interface AuthenticationService {
  User signup(SignupRequest signUpRequest);

  JwtAuthenticationResponse login(LoginRequest loginRequest);

  JwtAuthenticationResponse refreshToken(
    RefreshTokenRequest refreshTokenRequest
  );
}
