package com.example.secu;

import com.example.secu.entity.Role;
import com.example.secu.entity.User;
import com.example.secu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecuApplication implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication.run(SecuApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    User admin = userRepository.findByRole(Role.ADMIN);

    if (admin == null) {
      User user = new User();

      user.setEmail("admin@gmail.com");
      user.setFirstname("admin");
      user.setLastname("admin");
      user.setRole(Role.ADMIN);
      user.setPassword(passwordEncoder.encode("admin"));

      userRepository.save(user);
    }
  }
}
