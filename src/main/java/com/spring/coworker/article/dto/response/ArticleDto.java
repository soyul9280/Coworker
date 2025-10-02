package com.spring.coworker.article.dto.response;

import com.spring.coworker.user.entity.User;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleDto {
    private UUID articleId;
    private Instant createdAt;
    private Instant updatedAt;
    private String title;
    private String content;
    private String imageUrl;
    private WriterDto writer;
}
