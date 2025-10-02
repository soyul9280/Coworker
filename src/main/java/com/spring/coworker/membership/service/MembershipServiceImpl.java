package com.spring.coworker.membership.service;

import com.spring.coworker.global.SortDirection;
import com.spring.coworker.global.response.PageResponse;
import com.spring.coworker.group.entity.Group;
import com.spring.coworker.group.repository.GroupRepository;
import com.spring.coworker.membership.dto.request.MemberShipUpdateRequest;
import com.spring.coworker.membership.dto.request.MembershipCreateRequest;
import com.spring.coworker.membership.dto.request.MembershipSearchRequest;
import com.spring.coworker.membership.dto.response.MemberShipDto;
import com.spring.coworker.membership.entity.MemberShip;
import com.spring.coworker.membership.entity.MembershipRole;
import com.spring.coworker.membership.mapper.MembershipMapper;
import com.spring.coworker.membership.repository.MembershipRepository;
import com.spring.coworker.user.entity.User;
import com.spring.coworker.user.repository.UserRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
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

  @Override
  public MemberShipDto update(UUID membershipId,MemberShipUpdateRequest request) {
    MemberShip memberShip = membershipRepository.findById(membershipId)
        .orElseThrow(() -> new IllegalArgumentException("membership not found"));
    memberShip.updateRole(request.newRole());
    return membershipMapper.toMembershipDto(memberShip);
  }

  @Override
  public void delete(UUID membershipId) {
    MemberShip memberShip = membershipRepository.findById(membershipId)
        .orElseThrow(() -> new IllegalArgumentException("membership not found"));
    membershipRepository.deleteById(membershipId);
  }

  @Override
  public PageResponse searchMemberships(MembershipSearchRequest request) {
    String cursor = request.cursor();
    UUID idAfter=request.idAfter();
    int limit = request.limit();
    String sortBy = request.sortBy();
    SortDirection sortDirection = request.sortDirection();
    Instant joinedAt = request.joinedAt();
    MembershipRole roleEqual = request.roleEqual();

    Slice<MemberShip> slice = membershipRepository.searchMemberShips(cursor, idAfter, limit,
        sortBy, sortDirection, joinedAt, roleEqual);
    List<MemberShip> memberShips = slice.getContent();

    List<MemberShipDto> membershipDtos = memberShips.stream()
        .map(membershipMapper::toMembershipDto)
        .toList();

    boolean hasNext = slice.hasNext();

    MemberShip lastMembership = (membershipDtos.size()>0)? memberShips.get(membershipDtos.size()-1):null;

    Object nextCursor = null;
    UUID nextIdAfter = null;

    if(lastMembership != null&&hasNext) {
      switch(sortBy) {
        case "joinedAt":
          nextCursor = lastMembership.getJoinedAt();
          break;
        case "createdAt":
          nextCursor = lastMembership.getCreatedAt();
          break;
          default:
            throw new IllegalArgumentException("지원하지 않는 정렬 기준입니다.");
      }
      nextIdAfter = lastMembership.getId();
    }
    return new PageResponse(
        membershipDtos,
        nextCursor,
        nextIdAfter,
        slice.hasNext(),
        membershipRepository.getTotalCount(joinedAt,roleEqual),
        sortBy,
        sortDirection.name()
    );
  }


}
