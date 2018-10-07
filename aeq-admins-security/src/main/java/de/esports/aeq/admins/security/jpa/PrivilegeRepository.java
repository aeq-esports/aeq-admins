package de.esports.aeq.admins.security.jpa;

import de.esports.aeq.admins.security.domain.PrivilegeTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeTa, Long> {

    Optional<PrivilegeTa> findByName(String name);
}
