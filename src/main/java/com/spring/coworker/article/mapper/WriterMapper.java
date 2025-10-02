package com.spring.coworker.article.mapper;

import com.spring.coworker.article.dto.response.WriterDto;
import com.spring.coworker.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WriterMapper {

  public WriterDto toWriterDto(User writer) {
    return WriterDto.builder()
        .id(writer.getId())
        .name(writer.getName())
        .email(writer.getEmail())
        .build();
  }

}
