package com.spring.coworker.group.mapper;

import com.spring.coworker.group.dto.response.GroupDto;
import com.spring.coworker.group.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
  GroupDto toGroupDto(Group group);
}
