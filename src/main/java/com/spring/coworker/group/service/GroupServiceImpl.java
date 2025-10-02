package com.spring.coworker.group.service;

import com.spring.coworker.group.dto.request.GroupCreateRequest;
import com.spring.coworker.group.dto.request.GroupUpdateRequest;
import com.spring.coworker.group.dto.response.GroupDto;
import com.spring.coworker.group.entity.Group;
import com.spring.coworker.group.mapper.GroupMapper;
import com.spring.coworker.group.repository.GroupRepository;
import com.spring.coworker.membership.entity.MemberShip;
import com.spring.coworker.membership.entity.MembershipRole;
import com.spring.coworker.membership.repository.MembershipRepository;
import com.spring.coworker.user.entity.User;
import com.spring.coworker.user.repository.UserRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService {
  private final GroupRepository groupRepository;
  private final GroupMapper groupMapper;
  private final UserRepository userRepository;
  private final MembershipRepository membershipRepository;

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

    User creator = userRepository.findById(request.userId())
        .orElseThrow(() -> new IllegalArgumentException("user not found"));

    MemberShip memberShip = MemberShip.builder()
        .user(creator)
        .group(group)
        .joinedAt(Instant.now())
        .role(MembershipRole.Leader)
        .build();
    membershipRepository.save(memberShip);

    return groupMapper.toGroupDto(result);
  }

  @Override
  public GroupDto updateGroup(UUID groupId, GroupUpdateRequest request) {
    Group group = groupRepository.findById(groupId)
        .orElseThrow(() -> new IllegalArgumentException("Group not found"));
    group.updateName(request.name());
    //TODO: S3설정 후 이미지 로직 생성하기
    return groupMapper.toGroupDto(group);
  }

  @Override
  public void deleteGroup(UUID groupId) {
    Group group = groupRepository.findById(groupId)
        .orElseThrow(() -> new IllegalArgumentException("Group not found"));

    membershipRepository.deleteAllByGroupId(groupId);
    groupRepository.deleteById(groupId);
  }
}
