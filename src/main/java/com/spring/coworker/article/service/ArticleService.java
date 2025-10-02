package com.spring.coworker.article.service;

import com.spring.coworker.article.dto.request.ArticleCreateRequest;
import com.spring.coworker.article.dto.response.ArticleDto;

public interface ArticleService {
  ArticleDto createArticle(ArticleCreateRequest request);
}
