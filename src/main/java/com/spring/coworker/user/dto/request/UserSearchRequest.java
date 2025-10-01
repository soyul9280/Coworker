package com.spring.coworker.user.dto.request;

import com.spring.coworker.global.SortDirection;
import com.spring.coworker.user.entity.Role;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UserSearchRequest(
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
    String emailLike,
    Role roleEqual
) {

}
