package com.spring.coworker.group.controller;

import com.spring.coworker.group.dto.request.GroupCreateRequest;
import com.spring.coworker.group.dto.request.GroupUpdateRequest;
import com.spring.coworker.group.dto.response.GroupDto;
import com.spring.coworker.group.service.GroupService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class GroupController {
  private final GroupService groupService;

  @PostMapping("")
  public ResponseEntity<GroupDto> createGroup(@Valid @RequestBody GroupCreateRequest groupCreateRequest) {
    GroupDto group = groupService.createGroup(groupCreateRequest);
    return ResponseEntity.ok(group);
  }

  @PatchMapping("/{groupId}")
  public ResponseEntity<GroupDto> updateGroup(@PathVariable UUID groupId, @Valid @RequestBody GroupUpdateRequest groupUpdateRequest) {
    GroupDto group = groupService.updateGroup(groupId, groupUpdateRequest);
    return ResponseEntity.ok(group);
  }

  @DeleteMapping("/{groupId}")
  public ResponseEntity<Void> deleteGroup(@PathVariable UUID groupId) {
    groupService.deleteGroup(groupId);
    return ResponseEntity.noContent().build();
  }

}
