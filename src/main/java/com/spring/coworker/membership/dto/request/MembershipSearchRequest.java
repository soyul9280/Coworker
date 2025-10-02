package com.spring.coworker.membership.dto.request;

import com.spring.coworker.global.SortDirection;
import com.spring.coworker.membership.entity.MembershipRole;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record MembershipSearchRequest(
    String cursor,
    UUID idAfter,
    @NotNull
    @Min(1)
    @Max(100)
    int limit,
    @NotBlank
    String sortBy,
    @NotNull
    SortDirection sortDirection,
    Instant joinedAt,
    MembershipRole roleEqual
) {

}
