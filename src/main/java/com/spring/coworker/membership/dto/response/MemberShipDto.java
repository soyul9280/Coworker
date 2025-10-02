package com.spring.coworker.membership.dto.response;

import com.spring.coworker.group.entity.Group;
import com.spring.coworker.membership.entity.MembershipRole;
import com.spring.coworker.user.entity.User;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberShipDto {
  private final UUID id;
  private final User user;
  private final Group group;
  private final Instant createdAt;
  private final Instant updatedAt;
  private final Instant joinedAt;
  private final MembershipRole role;
}
