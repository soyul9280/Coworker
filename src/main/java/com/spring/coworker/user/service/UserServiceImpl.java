package com.spring.coworker.user.service;

import com.spring.coworker.user.dto.mapper.UserMapper;
import com.spring.coworker.user.dto.request.UserCreateRequest;
import com.spring.coworker.user.dto.response.UserDto;
import com.spring.coworker.user.entity.User;
import com.spring.coworker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public UserDto createUser(UserCreateRequest userCreateRequest) {
    if (userRepository.existsByEmail(userCreateRequest.email())) {
      throw new IllegalArgumentException();
    }
    User user = userRepository.save(
        User.builder()
            .name(userCreateRequest.name())
            .email(userCreateRequest.email())
            .password(userCreateRequest.password())
            .build()
    );
    return userMapper.toDto(user);
  }
}
