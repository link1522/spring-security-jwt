package com.example.secu.service.impl;

import com.example.secu.dto.request.LoginRequest;
import com.example.secu.dto.request.RefreshTokenRequest;
import com.example.secu.dto.request.SignupRequest;
import com.example.secu.dto.response.JwtAuthenticationResponse;
import com.example.secu.entity.Role;
import com.example.secu.entity.User;
import com.example.secu.repository.UserRepository;
import com.example.secu.service.AuthenticationService;
import com.example.secu.service.JwtService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  private final JwtService jwtService;

  @Override
  public User signup(SignupRequest signupRequest) {
    User user = new User();

    user.setEmail(signupRequest.getEmail());
    user.setFirstname(signupRequest.getFirstname());
    user.setLastname(signupRequest.getLastname());
    user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
    user.setRole(Role.USER);

    return userRepository.save(user);
  }

  @Override
  public JwtAuthenticationResponse login(LoginRequest loginRequest) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginRequest.getEmail(),
        loginRequest.getPassword()
      )
    );

    User user = userRepository
      .findByEmail(loginRequest.getEmail())
      .orElseThrow(() ->
        new IllegalArgumentException("Invalid email or password")
      );

    String jwt = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(
      new HashMap<>(),
      user
    );

    JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

    jwtAuthenticationResponse.setToken(jwt);
    jwtAuthenticationResponse.setRefreshToken(refreshToken);

    return jwtAuthenticationResponse;
  }

  @Override
  public JwtAuthenticationResponse refreshToken(
    RefreshTokenRequest refreshTokenRequest
  ) {
    String userEmail = jwtService.extractUsername(
      refreshTokenRequest.getToken()
    );

    User user = userRepository.findByEmail(userEmail).orElseThrow();

    if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
      String jwt = jwtService.generateToken(user);

      JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

      jwtAuthenticationResponse.setToken(jwt);
      jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

      return jwtAuthenticationResponse;
    }

    return null;
  }
}
