package com.example.secu.controller;

import com.example.secu.dto.request.LoginRequest;
import com.example.secu.dto.request.RefreshTokenRequest;
import com.example.secu.dto.request.SignupRequest;
import com.example.secu.dto.response.JwtAuthenticationResponse;
import com.example.secu.entity.User;
import com.example.secu.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/signup")
  public ResponseEntity<User> signup(@RequestBody SignupRequest signupRequest) {
    User user = authenticationService.signup(signupRequest);

    return ResponseEntity.ok(user);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtAuthenticationResponse> login(
    @RequestBody LoginRequest loginRequest
  ) {
    JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.login(
      loginRequest
    );

    return ResponseEntity.ok(jwtAuthenticationResponse);
  }

  @PostMapping("/refresh")
  public ResponseEntity<JwtAuthenticationResponse> refresh(
    @RequestBody RefreshTokenRequest refreshTokenRequest
  ) {
    JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.refreshToken(
      refreshTokenRequest
    );

    return ResponseEntity.ok(jwtAuthenticationResponse);
  }
}
