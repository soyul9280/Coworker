package com.spring.coworker.user.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.coworker.global.SortDirection;
import com.spring.coworker.user.entity.QUser;
import com.spring.coworker.user.entity.Role;
import com.spring.coworker.user.entity.User;
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
public class UserCustomRepositoryImpl implements UserCustomRepository{

  private final JPAQueryFactory queryFactory;

  //원하는 개수만큼 잘라서 가져오기
  @Override
  public Slice<User> searchUsers(String cursor, UUID idAfter, int limit, String sortBy,
      SortDirection sortDirection, String emailLike, Role roleEqual) {
    QUser user = QUser.user;
    //정렬방향
    Order direction = (sortDirection.equals(SortDirection.ASCENDING)) ? Order.ASC : Order.DESC;
    //어떤 기준으로 정렬할지 담을 그릇
    OrderSpecifier<?> orderSpecifier;

    //정렬기준 : 이메일 , 가입날짜
    switch (sortBy) {
      case "email":
        orderSpecifier = new OrderSpecifier<>(direction, user.email);
        break;
      case "createdAt":
        orderSpecifier = new OrderSpecifier<>(direction, user.createdAt);
        break;
      default:
        throw new IllegalArgumentException("지원하지 않는 정렬 기준입니다.");
    }

    //필터링: 이메일, 권한/ 조건들을 모아서 나중에 쿼리에 붙일것
    BooleanBuilder where = new BooleanBuilder();

    where.and((emailLike==null||emailLike.isBlank()) ?null
        :user.email.containsIgnoreCase(emailLike));//대소문자 무시
    where.and((roleEqual ==null)?null:user.role.eq(roleEqual));

    //정렬기준과 커서에 따른 필터링
    //커서 = 다음페이지 어디서부터 보여줄지 기준값
    //커서가 비어있지 않다면 페이지의 다음 사용자들을 찾는 조건 추가
    if(cursor != null &&!cursor.isBlank()) {
      switch (sortBy) {
        case "email":
          where.and((direction.equals(Order.ASC))
          ? user.email.gt(cursor)
              : user.email.lt(cursor));
          break;
          case "createdAt":
            //문자열인 커서를 시간으로 바꾸기 +id로 조건걸어서 중복 없이 정렬
            Instant cursorInstant = Instant.parse(cursor);
            where.and((direction.equals(Order.ASC))
                ?user.createdAt.gt(cursorInstant)
                .or(user.createdAt.eq(cursorInstant).and(user.id.gt(idAfter)))
                : user.createdAt.lt(cursorInstant)
                    .or(user.createdAt.eq(cursorInstant).and(user.id.lt(idAfter))));
            break;
            default:
              throw new IllegalArgumentException("지원하지 않는 정렬 기준입니다.");
      }
    }

    JPAQuery<User> query =
        queryFactory.selectFrom(user);
    query.where(where);
    query.orderBy(orderSpecifier);
    //limit+1 = 다음페이지가 있는지 확인
    query.limit(limit+1);
    List<User> users = query.fetch();
    boolean hasNext = users.size() > limit;
    if(hasNext) {
      //다음 정보가 있는지 확인 위해 limit+1했었으니 조회는 정확히 Limit만큼 보여주기
      users.remove(users.size()-1);
    }
    //한페이지만 보여줌/ 지금 페이지 번호 안쓰니 0고정
    return new SliceImpl<>(users, PageRequest.of((0), limit), hasNext);
  }

  @Override
  public Long getTotalCount(String emailLike, Role roleEqual) {
    QUser user = QUser.user;
    BooleanBuilder where = new BooleanBuilder();
    where.and((emailLike==null||emailLike.isBlank()) ?null
        :user.email.containsIgnoreCase(emailLike));
    where.and((roleEqual ==null)?null:user.role.eq(roleEqual));
    return queryFactory.select(user.count())
        .from(user)
        .where(where)
        .fetchOne();
  }
}
