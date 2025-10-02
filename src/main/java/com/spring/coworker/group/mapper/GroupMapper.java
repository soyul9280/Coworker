package com.spring.coworker.group.mapper;

import com.spring.coworker.group.dto.response.GroupDto;
import com.spring.coworker.group.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupMapper {

  public GroupDto toGroupDto(Group group){
    return GroupDto.builder()
        .id(group.getId())
        .createdAt(group.getCreatedAt())
        .updatedAt(group.getUpdatedAt())
        .name(group.getName())
        .imageUrl(group.getImageUrl())
        .build();
  }
}
