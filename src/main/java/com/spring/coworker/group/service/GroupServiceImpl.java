package com.spring.coworker.group.service;

import com.spring.coworker.group.dto.request.GroupCreateRequest;
import com.spring.coworker.group.dto.request.GroupUpdateRequest;
import com.spring.coworker.group.dto.response.GroupDto;
import com.spring.coworker.group.entity.Group;
import com.spring.coworker.group.mapper.GroupMapper;
import com.spring.coworker.group.repository.GroupRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
  private final GroupRepository groupRepository;
  private final GroupMapper groupMapper;
  @Override
  public GroupDto createGroup(GroupCreateRequest request) {
    if (groupRepository.existsByName(request.name())) {
      throw new IllegalArgumentException("Group name already exists");
    }
    Group group = Group.builder()
        .name(request.name())
        .imageUrl(request.imageUrl())
        .build();
    Group result = groupRepository.save(group);
    return groupMapper.toGroupDto(result);
  }

  @Override
  public GroupDto updateGroup(GroupUpdateRequest request) {
    return null;
  }

  @Override
  public void deleteGroup(UUID groupId) {

  }
}
