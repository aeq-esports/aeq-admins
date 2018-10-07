package de.esports.aeq.admins.security.jpa;

import de.esports.aeq.admins.security.domain.RoleTa;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleTa, Long> {

    @EntityGraph(value = "graph.RoleTa.privileges", type = EntityGraph.EntityGraphType.LOAD)
    Optional<RoleTa> findOneByName(String name);
}
