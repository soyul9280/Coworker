package com.spring.coworker.user.repository;

import com.spring.coworker.global.SortDirection;
import com.spring.coworker.user.entity.Role;
import com.spring.coworker.user.entity.User;
import java.util.UUID;
import org.springframework.data.domain.Slice;

public interface UserCustomRepository {

  Slice<User> searchUsers(
      String cursor,
      UUID idAfter,
      int limit,
      String sortBy,
      SortDirection sortDirection,
      String emailLike,
      Role roleEqual
  );

  Long getTotalCount(
      String emailLike, Role roleEqual
  );

}
