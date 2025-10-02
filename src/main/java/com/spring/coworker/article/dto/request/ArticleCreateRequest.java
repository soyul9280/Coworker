package com.spring.coworker.article.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ArticleCreateRequest(
    @NotNull(message = "제목이 null이어서는 안됩니다.")
    @Size(min = 1, max = 20, message = "제목은 1-20자 사이여야 합니다.")
    String title,
    @NotNull(message = "내용이 null이어서는 안됩니다.")
    String content,
    String imageUrl
) {

}
