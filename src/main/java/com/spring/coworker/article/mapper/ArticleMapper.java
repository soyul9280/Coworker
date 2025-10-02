package com.spring.coworker.article.mapper;

import com.spring.coworker.article.dto.response.ArticleDto;
import com.spring.coworker.article.dto.response.WriterDto;
import com.spring.coworker.article.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleMapper {
  public ArticleDto toArticleDto(Article article, WriterDto writerDto){
    return ArticleDto.builder()
        .articleId(article.getId())
        .createdAt(article.getCreatedAt())
        .updatedAt(article.getUpdatedAt())
        .title(article.getTitle())
        .content(article.getContent())
        .imageUrl(article.getImageUrl())
        .writer(writerDto)
        .build();
  }
}
