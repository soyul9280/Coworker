package com.spring.coworker.membership.service;

import com.spring.coworker.global.response.PageResponse;
import com.spring.coworker.membership.dto.request.MemberShipUpdateRequest;
import com.spring.coworker.membership.dto.request.MembershipCreateRequest;
import com.spring.coworker.membership.dto.request.MembershipSearchRequest;
import com.spring.coworker.membership.dto.response.MemberShipDto;
import java.util.UUID;

public interface MembershipService {
  MemberShipDto create(MembershipCreateRequest request);
  MemberShipDto update(UUID membershipId,MemberShipUpdateRequest request);
  void delete(UUID membershipId);
  PageResponse searchMemberships(MembershipSearchRequest request);
}
