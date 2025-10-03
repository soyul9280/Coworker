package com.spring.coworker.article.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WriterDto {
  private UUID id;
  private String name;
  private String email;

  @Builder
  public WriterDto(UUID id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }
}
