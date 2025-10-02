package com.spring.coworker.membership.dto.request;

import com.spring.coworker.membership.entity.MembershipRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.Instant;
import java.util.UUID;

public record MembershipCreateRequest(
    @NotNull(message = "사용자 ID는 필수입니다")
    UUID userId,

    @NotNull(message = "그룹 ID는 필수입니다")
    UUID groupId,

    @NotNull(message = "가입한 날짜는 필수입니다")
    @PastOrPresent(message = "마지막 읽은 시간은 현재 또는 과거 시간이어야 합니다")
    Instant joinedAt,

    @NotNull(message = "역할은 필수입니다")
    MembershipRole role
) {

}
