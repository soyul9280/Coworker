package com.spring.coworker.membership.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.coworker.global.SortDirection;
import com.spring.coworker.membership.entity.MemberShip;
import com.spring.coworker.membership.entity.MembershipRole;
import com.spring.coworker.membership.entity.QMemberShip;
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
public class MembershipCustomRepositoryImpl implements MembershipCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Slice<MemberShip> searchMemberShips(String cursor, UUID idAfter, int limit, String sortBy,
      SortDirection sortDirection, Instant joinedAt, MembershipRole membershipRole) {
    QMemberShip memberShip = QMemberShip.memberShip;
    Order direction = sortDirection.equals(SortDirection.ASCENDING) ? Order.ASC : Order.DESC;

    OrderSpecifier<?> orderSpecifier;

    switch (sortBy) {
      case "joinedAt":
        orderSpecifier = new OrderSpecifier<>(direction, memberShip.joinedAt);
        break;
        case "createdAt":
          orderSpecifier = new OrderSpecifier<>(direction, memberShip.createdAt);
          break;
          default:
            throw new IllegalArgumentException("지원하지 않는 정렬 기준입니다.");
    }

    BooleanBuilder where = new BooleanBuilder();
    where.and(
        (joinedAt==null)?null:memberShip.joinedAt.eq(joinedAt));
    where.and(
        (membershipRole==null)?null:memberShip.role.eq(membershipRole));

    if(cursor != null||!cursor.isBlank()) {
      switch (sortBy){
        case "joinedAt":
          Instant cursorInstant = Instant.parse(cursor);
          where.and((direction.equals(Order.ASC))
              ?memberShip.joinedAt.gt(cursorInstant)
             : memberShip.joinedAt.lt(cursorInstant)
          );
          break;
          case "createdAt":
            Instant cursorCreatedInstant = Instant.parse(cursor);
            where.and((direction.equals(Order.ASC))
                ?memberShip.createdAt.gt(cursorCreatedInstant)
                .or(memberShip.createdAt.eq(cursorCreatedInstant).and(memberShip.id.gt(idAfter)))
                : memberShip.createdAt.lt(cursorCreatedInstant)
                    .or(memberShip.createdAt.eq(cursorCreatedInstant).and(memberShip.id.lt(idAfter)))
            );
            break;
            default:
              throw new IllegalArgumentException("지원하지 않는 정렬 기준입니다.");
      }
    }

    JPAQuery<MemberShip> query = queryFactory.selectFrom(memberShip);
    query.where(where);
    query.orderBy(orderSpecifier);
    query.limit(limit+1);
    List<MemberShip> memberships = query.fetch();
    boolean hasNext = memberships.size() > limit;
    if(hasNext) {
      memberships.remove(memberships.size()-1);
    }
    return new SliceImpl<>(memberships, PageRequest.of((0),limit), hasNext);

  }

  @Override
  public Long getTotalCount(Instant joinedAt, MembershipRole membershipRole) {
    QMemberShip memberShip = QMemberShip.memberShip;
    BooleanBuilder where = new BooleanBuilder();
    where.and((joinedAt==null)?null:memberShip.joinedAt.eq(joinedAt));
    where.and((membershipRole==null)?null:memberShip.role.eq(membershipRole));
    return queryFactory.select(memberShip.count())
        .from(memberShip)
        .where(where)
        .fetchOne();
  }
}
