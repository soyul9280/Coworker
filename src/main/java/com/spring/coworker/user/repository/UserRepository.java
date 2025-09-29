package com.spring.coworker.user.repository;

import com.spring.coworker.user.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
  boolean existsByEmail(String email);
}
