package com.spring.coworker.group.dto.request;

import com.spring.coworker.global.SortDirection;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GroupSearchRequest(
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
    String nameLike
) {

}
