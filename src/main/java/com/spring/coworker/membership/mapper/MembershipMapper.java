package com.spring.coworker.membership.mapper;

import com.spring.coworker.membership.dto.response.MemberShipDto;
import com.spring.coworker.membership.entity.MemberShip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MembershipMapper {
  public MemberShipDto toMembershipDto(MemberShip membership) {
    return MemberShipDto.builder()
        .id(membership.getId())
        .createdAt(membership.getCreatedAt())
        .updatedAt(membership.getUpdatedAt())
        .user(membership.getUser())
        .group(membership.getGroup())
        .joinedAt(membership.getJoinedAt())
        .role(membership.getRole())
        .build();
  }

}
