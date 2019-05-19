package de.esports.aeq.admins.security.impl.jpa;

import de.esports.aeq.admins.security.impl.jpa.entity.RoleTa;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleTa, Long> {

    @EntityGraph(value = "graph.RoleTa.privileges", type = EntityGraph.EntityGraphType.LOAD)
    Optional<RoleTa> findOneByName(String name);
}
