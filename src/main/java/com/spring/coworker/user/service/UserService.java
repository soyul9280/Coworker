package com.spring.coworker.user.service;

import com.spring.coworker.user.dto.request.UserCreateRequest;
import com.spring.coworker.user.dto.response.UserDto;

public interface UserService {
  UserDto createUser(UserCreateRequest userCreateRequest);
}
