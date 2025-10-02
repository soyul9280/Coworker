package com.spring.coworker.article.service;

import com.spring.coworker.article.dto.request.ArticleCreateRequest;
import com.spring.coworker.article.dto.request.ArticleUpdaterRequest;
import com.spring.coworker.article.dto.response.ArticleDto;
import com.spring.coworker.article.dto.response.WriterDto;
import com.spring.coworker.article.entity.Article;
import com.spring.coworker.article.mapper.ArticleMapper;
import com.spring.coworker.article.mapper.WriterMapper;
import com.spring.coworker.article.repository.ArticleRepository;
import com.spring.coworker.user.entity.User;
import com.spring.coworker.user.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
  private final ArticleRepository articleRepository;
  private final ArticleMapper articleMapper;
  private final UserRepository userRepository;
  private final WriterMapper writerMapper;

  @Override
  public ArticleDto createArticle(ArticleCreateRequest request) {
    if (articleRepository.existsByTitle(request.title())) {
      throw new IllegalArgumentException("Article title already exists");
    }
    User writer = userRepository.findById(request.writerId())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    Article result = Article.builder()
        .title(request.title())
        .content(request.content())
        .imageUrl(request.imageUrl())
        .user(writer)
        .build();

    articleRepository.save(result);

    WriterDto writerDto = writerMapper.toWriterDto(writer);
    return articleMapper.toArticleDto(result, writerDto);

  }

  @Override
  public ArticleDto updateArticle(UUID articleId,ArticleUpdaterRequest request) {
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new IllegalArgumentException("Article not found"));
    article.updateArticle(request);
    WriterDto writerDto = writerMapper.toWriterDto(article.getWriter());
    return articleMapper.toArticleDto(article, writerDto);
  }

  @Override
  public void deleteArticle(UUID articleId) {
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new IllegalArgumentException("Article not found"));
    articleRepository.deleteById(articleId);
  }
}
