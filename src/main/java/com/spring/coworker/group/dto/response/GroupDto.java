package com.spring.coworker.group.dto.response;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupDto {
  private final UUID id;
  private final Instant createdAt;
  private final String name;
  private final String imageUrl;
}
