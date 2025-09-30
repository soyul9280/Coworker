package com.spring.coworker.user.service;

import com.spring.coworker.user.dto.mapper.UserMapper;
import com.spring.coworker.user.dto.request.ChangePasswordRequest;
import com.spring.coworker.user.dto.request.ProfileUpdateRequest;
import com.spring.coworker.user.dto.request.UserCreateRequest;
import com.spring.coworker.user.dto.request.UserRoleUpdateRequest;
import com.spring.coworker.user.dto.response.ProfileDto;
import com.spring.coworker.user.dto.response.UserDto;
import com.spring.coworker.user.entity.User;
import com.spring.coworker.user.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
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
    return userMapper.toUserDto(user);
  }

  @Transactional(readOnly = true)
  @Override
  public ProfileDto findProfile(UUID userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("user not found"));
    return userMapper.toProfileDto(user);
  }

  @Override
  public ProfileDto updateProfile(UUID userId, ProfileUpdateRequest profileUpdateRequest) {
    //TODO: S3세팅 완료되면 profileImageUrl수정
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("user not found"));
    user.updateProfile(profileUpdateRequest.name(), profileUpdateRequest.profileImageUrl());
    return userMapper.toProfileDto(user);
  }

  @Override
  public void updatePassword(UUID userId, ChangePasswordRequest changePasswordRequest) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("user not found"));
    String newPassword = changePasswordRequest.password();
    user.updatePassword(newPassword);
  }

  @Override
  public UserDto updateRole(UUID userId, UserRoleUpdateRequest userRoleUpdateRequest) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("user not found"));
    user.updateRole(userRoleUpdateRequest.role());
    return userMapper.toUserDto(user);
  }
}
