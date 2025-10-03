package com.spring.coworker.article.controller;

import com.spring.coworker.article.dto.request.ArticleCreateRequest;
import com.spring.coworker.article.dto.request.ArticleUpdaterRequest;
import com.spring.coworker.article.dto.response.ArticleDto;
import com.spring.coworker.article.service.ArticleService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

  private final ArticleService articleService;

  @PostMapping("")
  public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleCreateRequest request) {
    ArticleDto result = articleService.createArticle(request);
    return ResponseEntity.ok(result);
  }

  @PatchMapping("/{articleId}")
  public ResponseEntity<ArticleDto> updateArticle(@PathVariable UUID articleId
  ,@Valid @RequestBody ArticleUpdaterRequest request) {
    ArticleDto result = articleService.updateArticle(articleId, request);
    return ResponseEntity.ok(result);
  }

  @DeleteMapping("{/articleId}")
  public ResponseEntity<Void> deleteArticle(@PathVariable UUID articleId) {
    articleService.deleteArticle(articleId);
    return ResponseEntity.noContent().build();
  }
}
