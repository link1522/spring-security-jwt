package com.example.secu.dto.response;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

  private String token;
  private String refreshToken;
}
