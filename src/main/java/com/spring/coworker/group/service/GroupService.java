package com.spring.coworker.group.service;

import com.spring.coworker.group.dto.request.GroupCreateRequest;
import com.spring.coworker.group.dto.request.GroupUpdateRequest;
import com.spring.coworker.group.dto.response.GroupDto;
import java.util.UUID;

public interface GroupService {
 GroupDto createGroup(GroupCreateRequest request);
 GroupDto updateGroup(GroupUpdateRequest request);
 void deleteGroup(UUID groupId);


}
