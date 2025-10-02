package com.spring.coworker.group.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.coworker.global.SortDirection;
import com.spring.coworker.group.entity.Group;
import com.spring.coworker.group.entity.QGroup;
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
public class GroupCustomRepositoryImpl implements GroupCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Slice<Group> searchGroups(String cursor, UUID idAfter, int limit, String sortBy,
      SortDirection direction, String nameLike) {
    QGroup group = QGroup.group;
    //정렬방향
    Order sortDirection = (direction.equals(SortDirection.ASCENDING)) ? Order.ASC : Order.DESC;

    OrderSpecifier<?> orderSpecifier;

    //정렬기준 설정
    switch (sortBy) {
      case "name":
        orderSpecifier = new OrderSpecifier<>(sortDirection, group.name);
        break;
        case "createdAt":
          orderSpecifier = new OrderSpecifier<>(sortDirection, group.createdAt);
          break;
          default:
            throw new IllegalArgumentException("지원하지 않는 정렬 기준입니다.");
    }

    //필터링: 이름
    BooleanBuilder where = new BooleanBuilder();
    where.and(
        (nameLike == null || nameLike.isBlank()) ? null : group.name.containsIgnoreCase(nameLike));

    //정렬에 따른 쿼리 설정
    if(cursor != null || !cursor.isBlank()) {
      switch (sortBy){
        case "name":
          where.and((sortDirection.equals(Order.ASC))
              ?group.name.gt(cursor)
              :group.name.lt(cursor));
          break;
          case "createdAt":
            Instant cursorInstant = Instant.parse(cursor);
            where.and((sortDirection.equals(Order.ASC))
            ?group.createdAt.gt(cursorInstant)
                .or(group.createdAt.eq(cursorInstant).and(group.id.gt(idAfter)))
                :group.createdAt.lt(cursorInstant)
                    .or(group.createdAt.eq(cursorInstant).and(group.id.lt(idAfter))));
            break;
            default:
              throw new IllegalArgumentException("지원하지 않는 정렬 기준입니다.");
      }
    }
    JPAQuery<Group> query = queryFactory.selectFrom(group);
    query.where(where);
    query.orderBy(orderSpecifier);
    query.limit(limit+1);
    List<Group> groups = query.fetch();
    boolean hasNext = groups.size() > limit;
    if (hasNext) {
      groups.remove(groups.size()-1);
    }
    return new SliceImpl<>(groups, PageRequest.of((0),limit),hasNext);
  }

  @Override
  public Long getTotalGroups(String nameLike) {
    QGroup group = QGroup.group;
    BooleanBuilder where = new BooleanBuilder();
    where.and((nameLike == null || nameLike.isBlank()) ? null : group.name.containsIgnoreCase(nameLike));
    return queryFactory.select(group.count())
        .from(group)
        .where(where)
        .fetchOne();
  }
}
