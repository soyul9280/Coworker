package com.spring.coworker.article.entity;

import com.spring.coworker.article.dto.request.ArticleUpdaterRequest;
import com.spring.coworker.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "articles")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", updatable = true)
  private Instant updatedAt;

  @Column(nullable = false, unique = true)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(name = "image")
  private String imageUrl;

  @Column(name = "like_count")
  private int likeCount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "writer_id",nullable = false)
  private User writer;

  @Builder
  public Article(String title, Instant createdAt,Instant updatedAt,String content, String imageUrl,User user) {
    this.title = title;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.content = content;
    this.imageUrl = imageUrl;
    this.writer = user;
  }

  public void updateArticle(ArticleUpdaterRequest request) {
    if (request.title() != null && !request.title().isBlank()) {
      if(!title.equals(request.title())) {
        title = request.title();
      }
    }
    if (request.content() != null && !request.content().isBlank()) {
      if(!content.equals(request.content())) {
        content = request.content();
      }
    }
  }

}
