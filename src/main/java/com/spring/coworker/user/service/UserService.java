package com.spring.coworker.user.service;

import com.spring.coworker.user.dto.request.ProfileUpdateRequest;
import com.spring.coworker.user.dto.request.UserCreateRequest;
import com.spring.coworker.user.dto.response.ProfileDto;
import com.spring.coworker.user.dto.response.UserDto;
import java.util.UUID;

public interface UserService {
  UserDto createUser(UserCreateRequest userCreateRequest);
  ProfileDto findProfile(UUID userId);
  ProfileDto updateProfile(UUID userId, ProfileUpdateRequest profileUpdateRequest);
}
