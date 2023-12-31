package com.example.secu.repository;

import com.example.secu.entity.Role;
import com.example.secu.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  User findByRole(Role role);
}
