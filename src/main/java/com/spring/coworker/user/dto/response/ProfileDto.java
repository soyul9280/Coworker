package com.spring.coworker.user.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileDto {
  private final UUID userId;
  private final String name;
  private final String profileImageUrl;
}
