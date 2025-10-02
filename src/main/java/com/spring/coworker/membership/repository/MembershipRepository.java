package com.spring.coworker.membership.repository;

import com.spring.coworker.membership.entity.MemberShip;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<UUID, MemberShip> {

}
