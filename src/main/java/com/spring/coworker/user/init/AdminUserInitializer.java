package com.spring.coworker.user.init;

import com.spring.coworker.user.entity.Role;
import com.spring.coworker.user.entity.User;
import com.spring.coworker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 사용자 관리
 * 어드민 기능: 서버 시작 시 계정 자동 초기화
 * email:admin@mail.com
 * name: admin
 * password: admin
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class AdminUserInitializer implements ApplicationRunner {

  private final UserRepository userRepository;
  @Override
  public void run(ApplicationArguments args) throws Exception {
    String name = "admin";
    String password = "admin";
    String email = "admin@mail.com";

    if (!userRepository.existsByEmail(email)
        && !userRepository.existsByName(name)) {
      User admin = userRepository.save(User.builder()
          .name(name)
          .email(email)
          .password(password)
          .role(Role.ADMIN)
          .build());
    }else{
      log.info("Admin User: {}  Already Exists",email);
    }
  }
}
