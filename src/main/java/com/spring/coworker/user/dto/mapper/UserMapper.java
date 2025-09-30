package com.spring.coworker.user.dto.mapper;

import com.spring.coworker.user.dto.response.ProfileDto;
import com.spring.coworker.user.dto.response.UserDto;
import com.spring.coworker.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDto(User user);

  @Mapping(source = "id",target = "userId")
  ProfileDto toProfileDto(User user);

}
