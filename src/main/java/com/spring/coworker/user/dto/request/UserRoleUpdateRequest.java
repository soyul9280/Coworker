package com.spring.coworker.user.dto.request;

import com.spring.coworker.user.entity.Role;

public record UserRoleUpdateRequest(
    Role role
) {

}
