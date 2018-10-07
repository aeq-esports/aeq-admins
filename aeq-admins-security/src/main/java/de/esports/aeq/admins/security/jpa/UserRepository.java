package de.esports.aeq.admins.security.jpa;

import de.esports.aeq.admins.security.domain.UserTa;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserTa, Long> {

    @EntityGraph(value = "graph.UserTa.roles.privileges", type = EntityGraph.EntityGraphType.LOAD)
    Optional<UserTa> findOneByUsername(String username);
}
