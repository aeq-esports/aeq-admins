package de.esports.aeq.admins.security.impl.jpa;

import de.esports.aeq.admins.security.impl.jpa.entity.UserTa;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserTa, Long> {

    //@EntityGraph(value = "graph.UserTa.roles.privileges", type = EntityGraph.EntityGraphType.LOAD)
    Optional<UserTa> findOneByUsername(String username);

    Collection<UserTa> findAllByAuthoritiesContains(Collection<String> authorities);

}
