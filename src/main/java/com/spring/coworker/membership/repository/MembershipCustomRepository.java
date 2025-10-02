package com.spring.coworker.membership.repository;

import com.spring.coworker.global.SortDirection;
import com.spring.coworker.membership.entity.MemberShip;
import com.spring.coworker.membership.entity.MembershipRole;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.domain.Slice;

public interface MembershipCustomRepository {

  Slice<MemberShip> searchMemberShips(
      String cursor,
      UUID idAfter,
      int limit,
      String sortBy,
      SortDirection sortDirection,
      Instant joinedAt,
      MembershipRole membershipRole
  );

  Long getTotalCount(Instant joinedAt, MembershipRole membershipRole);

}
