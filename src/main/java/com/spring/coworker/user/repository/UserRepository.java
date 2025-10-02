package com.spring.coworker.user.repository;

import com.spring.coworker.user.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>,UserCustomRepository {
  boolean existsByEmail(String email);
  boolean existsByName(String username);
  String email(String email);
}
