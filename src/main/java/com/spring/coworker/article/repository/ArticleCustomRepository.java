package com.spring.coworker.article.repository;

import com.spring.coworker.article.entity.Article;
import com.spring.coworker.global.SortDirection;
import java.util.UUID;
import org.springframework.data.domain.Slice;

public interface ArticleCustomRepository {
  Slice<Article> searchArticles(
      String cursor,
      UUID idAfter,
      int limit,
      String sortBy,
      SortDirection sortDirection,
      String titleLike
  );

  Long getTotalCount(String titleLike, int likeCount);

}
