package com.spring.coworker.article.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Builder
public class WriterDto {
  private UUID id;
  private String name;
  private String email;
}
