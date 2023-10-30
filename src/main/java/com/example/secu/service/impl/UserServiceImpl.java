package com.example.secu.service.impl;

import com.example.secu.repository.UserRepository;
import com.example.secu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        return userRepository
          .findByEmail(username)
          .orElseThrow(() -> new UsernameNotFoundException("User not found"));
      }
    };
  }
}
