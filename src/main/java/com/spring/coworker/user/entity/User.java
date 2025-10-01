package com.spring.coworker.user.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//builder로 만들도록 직접생성 막음
@EntityListeners(AuditingEntityListener.class)
public class User {
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
  private String email;

  @Column(name="nickname", nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  @Column(nullable = false)
  private Role role = Role.USER;

  @Column(name = "image")
  private String profileImageUrl;

  @Type(JsonType.class)
  @Column(name = "linked_oauth_providers",columnDefinition = "jsonb")
  private List<OAuthProvider> linkedOAuthProviders;

  @Builder
  private User(UUID id,
      Instant createdAt,
      Instant updatedAt,
      String email,
      String name,
      String password,
      Role role,
      String profileImageUrl,
      List<OAuthProvider> linkedOAuthProviders) {
      this.id = id;
      this.createdAt = createdAt;
      this.updatedAt = updatedAt;
      this.email = email;
      this.name = name;
      this.password = password;
      this.role = role;
      this.profileImageUrl = profileImageUrl;
      this.linkedOAuthProviders = linkedOAuthProviders;
  }

  public void updateProfile(String name, String profileImageUrl) {
    if (name != null) {
      this.name = name;
    }
    if (profileImageUrl != null) {
      this.profileImageUrl = profileImageUrl;
    }
  }

  public void updatePassword(String password) {
    if (password != null && !password.isBlank()) {
      this.password = password;
    }
  }

  public void updateRole(Role role) {
    if(role != null) {
      this.role = role;
    }
  }

}
