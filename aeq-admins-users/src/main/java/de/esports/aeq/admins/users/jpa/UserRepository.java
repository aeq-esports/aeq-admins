package de.esports.aeq.admins.users.jpa;

import de.esports.aeq.admins.users.domain.UserTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserTa, Long>,
        QuerydslPredicateExecutor<UserTa> {

    Optional<UserTa> findByTs3UId(String ts3UniqueId);

}
