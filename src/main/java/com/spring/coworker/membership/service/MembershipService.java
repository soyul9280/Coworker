package com.spring.coworker.membership.service;

import com.spring.coworker.membership.dto.request.MemberShipUpdateRequest;
import com.spring.coworker.membership.dto.request.MembershipCreateRequest;
import com.spring.coworker.membership.dto.response.MemberShipDto;
import java.util.UUID;

public interface MembershipService {
  MemberShipDto create(MembershipCreateRequest request);
  MemberShipDto update(UUID membershipId,MemberShipUpdateRequest request);
}
