package com.spring.coworker.user.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProfileUpdateRequest (
    @NotNull(message = "이름은 null이어서는 안됩니다.")
    @Size(min = 1, max = 20, message = "이름은 1-20자 사이여야 합니다.")
    String name,
    String profileImageUrl
){

}
