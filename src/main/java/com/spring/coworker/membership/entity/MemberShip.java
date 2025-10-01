package com.spring.coworker.membership.entity;

import com.spring.coworker.group.entity.Group;
import com.spring.coworker.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Table(name = "memberships")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberShip {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id",columnDefinition = "uuid")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "group_id",columnDefinition = "uuid")
  private Group group;

  @Column(columnDefinition = "timestamp with time zone", nullable = false)
  private Instant joinedAt;

  @Builder
  public MemberShip(User user, Group group, Instant joinedAt) {
    this.user = user;
    this.group = group;
    this.joinedAt = joinedAt;
  }

}
