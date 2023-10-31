package com.example.secu.service;

import com.example.secu.dto.SignupRequest;
import com.example.secu.entity.User;

public interface AuthenticationService {
  User signup(SignupRequest signUpRequest);
}
