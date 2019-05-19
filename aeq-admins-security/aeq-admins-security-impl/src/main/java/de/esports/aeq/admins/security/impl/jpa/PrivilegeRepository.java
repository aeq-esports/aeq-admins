package de.esports.aeq.admins.security.impl.jpa;

import de.esports.aeq.admins.security.impl.jpa.entity.PrivilegeTa;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeTa, Long> {

    Optional<PrivilegeTa> findByName(String name);
}
