package com.spring.coworker.article.repository;

import com.spring.coworker.article.entity.Article;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID>,ArticleCustomRepository {
  boolean existsByTitle(String title);

  UUID id(UUID id);
}
