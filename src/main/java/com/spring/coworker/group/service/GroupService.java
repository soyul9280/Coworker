package com.spring.coworker.group.service;

import com.spring.coworker.global.response.PageResponse;
import com.spring.coworker.group.dto.request.GroupCreateRequest;
import com.spring.coworker.group.dto.request.GroupSearchRequest;
import com.spring.coworker.group.dto.request.GroupUpdateRequest;
import com.spring.coworker.group.dto.response.GroupDto;
import java.util.UUID;

public interface GroupService {
 GroupDto createGroup(GroupCreateRequest request);
 GroupDto updateGroup(UUID groupId, GroupUpdateRequest request);
 void deleteGroup(UUID groupId);
 PageResponse searchGroups(GroupSearchRequest request);

}
