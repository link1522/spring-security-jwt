package com.example.secu.service.impl;

import com.example.secu.dto.SignupRequest;
import com.example.secu.entity.Role;
import com.example.secu.entity.User;
import com.example.secu.repository.UserRepository;
import com.example.secu.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public User signup(SignupRequest signupRequest) {
    User user = new User();

    user.setEmail(signupRequest.getEmail());
    user.setFirstname(signupRequest.getFirstname());
    user.setLastname(signupRequest.getLastname());
    user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
    user.setRole(Role.USER);

    return userRepository.save(user);
  }
}
