package com.spring.coworker.article.service;

import com.spring.coworker.article.dto.request.ArticleCreateRequest;
import com.spring.coworker.article.dto.request.ArticleSearchRequest;
import com.spring.coworker.article.dto.request.ArticleUpdaterRequest;
import com.spring.coworker.article.dto.response.ArticleDto;
import com.spring.coworker.article.entity.Article;
import com.spring.coworker.article.mapper.ArticleMapper;
import com.spring.coworker.article.mapper.WriterMapper;
import com.spring.coworker.article.repository.ArticleRepository;
import com.spring.coworker.global.SortDirection;
import com.spring.coworker.global.response.PageResponse;
import com.spring.coworker.user.entity.User;
import com.spring.coworker.user.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {
  private final ArticleRepository articleRepository;
  private final ArticleMapper articleMapper;
  private final UserRepository userRepository;

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

    return articleMapper.toArticleDto(result);

  }

  @Override
  public ArticleDto updateArticle(UUID articleId,ArticleUpdaterRequest request) {
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new IllegalArgumentException("Article not found"));
    article.updateArticle(request);
    return articleMapper.toArticleDto(article);
  }

  @Override
  public void deleteArticle(UUID articleId) {
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new IllegalArgumentException("Article not found"));
    articleRepository.deleteById(articleId);
  }

  @Transactional(readOnly = true)
  @Override
  public PageResponse searchArticles(ArticleSearchRequest request) {
    String cursor = request.cursor();
    UUID idAfter = request.idAfter();
    String sortBy = request.sortBy();
    SortDirection direction = request.sortDirection();
    int limit = request.limit();
    String titleLike= request.titleLike();

    Slice<Article> slice = articleRepository.searchArticles(cursor, idAfter, limit, sortBy,
        direction, titleLike);
    List<Article> articles = slice.getContent();

    List<ArticleDto> articleDtos = articles.stream()
        .map(articleMapper::toArticleDto)
        .toList();
    boolean hasNext = slice.hasNext();
    Article lastArticle = (articles.size() > 0) ? articles.get(articles.size() - 1) : null;
    Object nextCursor = null;
    UUID nextIdAfter = null;
    if(lastArticle != null&&hasNext) {
      switch (sortBy) {
        case "title":
          nextCursor = lastArticle.getTitle();
          break;
          case "createdAt":
            nextCursor = lastArticle.getCreatedAt();
            break;
        case "likeCount":
          nextCursor = lastArticle.getLikeCount();
          break;
        default:
          throw new IllegalArgumentException("지원하지 않는 정렬 기준입니다.");
      }
      nextIdAfter = lastArticle.getId();
    }
    return new PageResponse(
        articleDtos,
        nextCursor,
        nextIdAfter,
        slice.hasNext(),
        articleRepository.getTotalCount(titleLike),
        sortBy,
        direction.name()
    );

  }

  @Transactional(readOnly = true)
  @Override
  public ArticleDto findArticle(UUID articleId) {
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new IllegalArgumentException("Article not found"));
    return articleMapper.toArticleDto(article);
  }
}
