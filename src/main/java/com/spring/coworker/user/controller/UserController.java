package com.spring.coworker.user.controller;

import com.spring.coworker.global.SortDirection;
import com.spring.coworker.user.dto.request.ChangePasswordRequest;
import com.spring.coworker.user.dto.request.ProfileUpdateRequest;
import com.spring.coworker.user.dto.request.UserCreateRequest;
import com.spring.coworker.user.dto.request.UserRoleUpdateRequest;
import com.spring.coworker.user.dto.response.ProfileDto;
import com.spring.coworker.user.dto.response.UserDto;
import com.spring.coworker.user.dto.response.UserPageResponse;
import com.spring.coworker.user.entity.Role;
import com.spring.coworker.user.service.UserService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @PatchMapping("/{userId}/password")
  ResponseEntity<Void> updatePassword(@PathVariable UUID userId,
      @RequestBody ChangePasswordRequest request) {
    userService.updatePassword(userId, request);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{userId}/role")
  ResponseEntity<UserDto> updateRole(@PathVariable UUID userId,
      @RequestBody UserRoleUpdateRequest request) {
    UserDto result = userService.updateRole(userId, request);
    return ResponseEntity.ok(result);
  }

  @GetMapping("")
  ResponseEntity<UserPageResponse<UserDto>> searchUsers(
      @RequestParam(required = false) String cursor,
      @RequestParam(required = false) UUID idAfter,
      @RequestParam int limit,
      @RequestParam String sortBy,
      @RequestParam SortDirection sortDirection,
      @RequestParam(required = false) String emailLike,
      @RequestParam(required = false) Role roleEqual
  ){
    UserPageResponse<UserDto> result = userService.searchUsers(cursor, idAfter,
        limit, sortBy, sortDirection, emailLike, roleEqual);
    return ResponseEntity.ok(result);
  }
}
