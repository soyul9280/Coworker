package com.spring.coworker.user.controller;

import com.spring.coworker.user.dto.request.UserCreateRequest;
import com.spring.coworker.user.dto.response.UserDto;
import com.spring.coworker.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  @PostMapping("")
  public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserCreateRequest request) {
    UserDto result = userService.createUser(request);
    return ResponseEntity.ok(result);
  }

}
