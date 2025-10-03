package com.spring.coworker.article.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.coworker.article.entity.Article;
import com.spring.coworker.article.entity.QArticle;
import com.spring.coworker.global.SortDirection;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Slice<Article> searchArticles(String cursor, UUID idAfter, int limit, String sortBy,
      SortDirection sortDirection, String titleLike) {
    QArticle article = QArticle.article;
    Order direction = (sortDirection.equals(SortDirection.ASCENDING)) ? Order.ASC : Order.DESC;
    OrderSpecifier<?> orderSpecifier;
    switch (sortBy) {
      case "title":
        orderSpecifier = new OrderSpecifier<>(direction, article.title);
        break;
      case "createdAt":
        orderSpecifier = new OrderSpecifier<>(direction, article.createdAt);
        break;
      case "likeCount":
        orderSpecifier = new OrderSpecifier<>(direction, article.likeCount);
        break;
        //TODO:댓글수 정렬 추가하기
      default:
          throw new IllegalArgumentException("지원하지 않는 정렬 기준입니다.");
    }
    BooleanBuilder where = new BooleanBuilder();
    where.and((titleLike ==null||titleLike.isBlank())?null:article.title.containsIgnoreCase(titleLike));

    if(cursor != null&&!cursor.isBlank()) {
      switch (sortBy) {
        case "title":
          where.and((direction.equals(Order.ASC))
          ?article.title.gt(cursor)
              : article.title.lt(cursor));
          break;
          case "createdAt":
            Instant instantCursor = Instant.parse(cursor);
            where.and((direction.equals(Order.ASC))
            ?article.createdAt.gt(instantCursor)
                .or(article.createdAt.eq(instantCursor).and(article.id.gt(idAfter)))
                : article.createdAt.lt(instantCursor)
                    .or(article.createdAt.eq(instantCursor).and(article.id.lt(idAfter))));
            break;
        case "likeCount":
          int likeCursor = Integer.parseInt(cursor);
          where.and((direction.equals(Order.ASC))
          ?article.likeCount.gt(likeCursor)
              .or(article.likeCount.eq(likeCursor).and(article.id.gt(idAfter)))
              :article.likeCount.lt(likeCursor)
                  .or(article.likeCount.eq(likeCursor).and(article.id.lt(idAfter))));
          break;
          default:
            throw new IllegalArgumentException("지원하지 않는 정렬기준 입니다.");
      }
    }
    JPAQuery<Article> query = queryFactory.selectFrom(article);
    query.where(where);
    query.orderBy(orderSpecifier);
    query.limit(limit+1);
    List<Article> articles = query.fetch();
    boolean hasNext = articles.size() > limit;
    if(hasNext) {
      articles.remove(articles.size()-1);
    }

    return new SliceImpl<>(articles, PageRequest.of((0),limit),hasNext);
  }

  @Override
  public Long getTotalCount(String titleLike) {
    QArticle article = QArticle.article;
    BooleanBuilder where = new BooleanBuilder();
    where.and((titleLike ==null||titleLike.isBlank())?null:article.title.containsIgnoreCase(titleLike));
    return queryFactory.select(article.count())
        .from(article)
        .where(where)
        .fetchOne();
  }
}
