package com.spring.coworker.global.response;

import java.util.UUID;

public record PageResponse <T>(
    T data,
    Object cursor,
    Object nextCursor,
    UUID nextIdAfter,
    boolean hasNext,
    long totalCount
){

}
