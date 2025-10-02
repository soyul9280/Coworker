package com.spring.coworker.user.dto.response;

import com.spring.coworker.user.entity.OAuthProvider;
import com.spring.coworker.user.entity.Role;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
  private final UUID id;
  private final Instant createdAt;
  private final Instant updatedAt;
  private final String email;
  private final String name;
  private final Role role;
  private final List<OAuthProvider> linkedOAuthProviders;
 // private final List<Group> groups;
}
