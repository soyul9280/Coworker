package com.spring.coworker.article.service;

import com.spring.coworker.article.dto.request.ArticleCreateRequest;
import com.spring.coworker.article.dto.request.ArticleUpdaterRequest;
import com.spring.coworker.article.dto.response.ArticleDto;
import java.util.UUID;

public interface ArticleService {
  ArticleDto createArticle(ArticleCreateRequest request);
  ArticleDto updateArticle(UUID articleId, ArticleUpdaterRequest request);
  void deleteArticle(UUID articleId);
}
