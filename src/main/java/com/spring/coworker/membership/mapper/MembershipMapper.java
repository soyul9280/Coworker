package com.spring.coworker.membership.mapper;

import com.spring.coworker.membership.dto.response.MemberShipDto;
import com.spring.coworker.membership.entity.MemberShip;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MembershipMapper {
  MemberShipDto toMembershipDto(MemberShip membership);

}
