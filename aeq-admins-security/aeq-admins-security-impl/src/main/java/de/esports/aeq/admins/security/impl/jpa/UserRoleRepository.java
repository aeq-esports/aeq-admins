package de.esports.aeq.admins.security.impl.jpa;

import de.esports.aeq.admins.security.impl.jpa.entity.UserRoleTa;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleTa, Long> {

    @EntityGraph(value = "graph.UserRoleTa.privileges", type = EntityGraph.EntityGraphType.LOAD)
    Optional<UserRoleTa> findOneByName(String name);

    Collection<UserRoleTa> findAllByNames(Collection<String> names);
}
