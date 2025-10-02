package com.spring.coworker.membership.service;

import com.spring.coworker.membership.dto.request.MembershipCreateRequest;
import com.spring.coworker.membership.dto.response.MemberShipDto;

public interface MembershipService {
  MemberShipDto create(MembershipCreateRequest request);
}
