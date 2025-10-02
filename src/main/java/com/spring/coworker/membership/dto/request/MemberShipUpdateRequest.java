package com.spring.coworker.membership.dto.request;

import com.spring.coworker.membership.entity.MembershipRole;
import jakarta.validation.constraints.NotNull;

public record MemberShipUpdateRequest(
    @NotNull(message = "역할은 필수입니다")
    MembershipRole newRole
) {

}
