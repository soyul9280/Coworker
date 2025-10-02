package com.spring.coworker.group.repository;

import com.spring.coworker.global.SortDirection;
import com.spring.coworker.group.entity.Group;
import java.util.UUID;
import org.springframework.data.domain.Slice;

public interface GroupCustomRepository {
  Slice<Group> searchGroups(
      String cursor,
      UUID idAfter,
      int limit,
      String sortBy,
      SortDirection direction,
      String nameLike
  );

  Long getTotalGroups(String nameLike);
}
