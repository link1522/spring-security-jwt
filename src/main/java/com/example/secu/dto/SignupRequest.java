package com.example.secu.dto;

import lombok.Data;

@Data
public class SignupRequest {

  private String firstname;

  private String lastname;

  private String email;

  private String password;
}
