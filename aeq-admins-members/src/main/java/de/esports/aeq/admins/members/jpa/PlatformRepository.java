package de.esports.aeq.admins.members.jpa;

import de.esports.aeq.admins.members.jpa.entity.PlatformTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformRepository extends JpaRepository<PlatformTa, Long> {

    Optional<PlatformTa> findByName(String name);
}
