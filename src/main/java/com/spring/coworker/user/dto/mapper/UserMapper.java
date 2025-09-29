package com.spring.coworker.user.dto.mapper;

import com.spring.coworker.user.dto.response.UserDto;
import com.spring.coworker.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDto(User user);

}
