package com.spring.coworker.user.service;

import com.spring.coworker.global.SortDirection;
import com.spring.coworker.global.response.PageResponse;
import com.spring.coworker.user.dto.request.ChangePasswordRequest;
import com.spring.coworker.user.dto.request.ProfileUpdateRequest;
import com.spring.coworker.user.dto.request.UserCreateRequest;
import com.spring.coworker.user.dto.request.UserRoleUpdateRequest;
import com.spring.coworker.user.dto.request.UserSearchRequest;
import com.spring.coworker.user.dto.response.ProfileDto;
import com.spring.coworker.user.dto.response.UserDto;
import com.spring.coworker.user.dto.response.UserPageResponse;
import com.spring.coworker.user.entity.Role;
import java.util.UUID;

public interface UserService {
  UserDto createUser(UserCreateRequest userCreateRequest);
  ProfileDto findProfile(UUID userId);
  ProfileDto updateProfile(UUID userId, ProfileUpdateRequest profileUpdateRequest);
  void updatePassword(UUID userId, ChangePasswordRequest changePasswordRequest);
  UserDto updateRole(UUID userId, UserRoleUpdateRequest userRoleUpdateRequest);

  PageResponse searchUsers(UserSearchRequest userSearchRequest);
}
