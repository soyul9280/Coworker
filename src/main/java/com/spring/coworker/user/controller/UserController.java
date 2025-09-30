package com.spring.coworker.user.controller;

import com.spring.coworker.user.dto.request.ProfileUpdateRequest;
import com.spring.coworker.user.dto.request.UserCreateRequest;
import com.spring.coworker.user.dto.response.ProfileDto;
import com.spring.coworker.user.dto.response.UserDto;
import com.spring.coworker.user.service.UserService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

  @PatchMapping(value = "/{userId}/profiles"
      , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ResponseEntity<ProfileDto> updateProfile(
      @PathVariable UUID userId,
      @Valid @RequestBody ProfileUpdateRequest request,
      @RequestPart(required = false) MultipartFile profileImage
  ) {
    //TODO:S3세팅 완료 후 profileImage 업데이트 추가
    ProfileDto result = userService.updateProfile(userId, request);
    return ResponseEntity.ok(result);
  }
}
