package com.spring.coworker.membership.service;

import com.spring.coworker.group.entity.Group;
import com.spring.coworker.group.repository.GroupRepository;
import com.spring.coworker.membership.dto.request.MembershipCreateRequest;
import com.spring.coworker.membership.dto.response.MemberShipDto;
import com.spring.coworker.membership.entity.MemberShip;
import com.spring.coworker.membership.entity.MembershipRole;
import com.spring.coworker.membership.mapper.MembershipMapper;
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
public class MembershipServiceImpl implements MembershipService {

  private final MembershipRepository membershipRepository;
  private final MembershipMapper membershipMapper;
  private final UserRepository userRepository;
  private final GroupRepository groupRepository;

  @Override
  public MemberShipDto create(MembershipCreateRequest request) {
    UUID userId = request.userId();
    UUID groupId = request.groupId();
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("user not found"));
    Group group = groupRepository.findById(groupId)
        .orElseThrow(() -> new IllegalArgumentException("group not found"));
    if(membershipRepository.existsByUserIdAndGroupId(userId, groupId)) {
      throw new IllegalArgumentException("membership already exists");
    }

    Instant joinedAt = request.joinedAt();
    MembershipRole role = request.role();
    MemberShip memberShip = MemberShip.builder()
        .group(group)
        .user(user)
        .role(role)
        .joinedAt(joinedAt)
        .build();

    membershipRepository.save(memberShip);
    return membershipMapper.toMembershipDto(memberShip);
  }
}
