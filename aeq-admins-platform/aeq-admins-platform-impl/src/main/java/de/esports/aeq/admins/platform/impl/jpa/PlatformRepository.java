package de.esports.aeq.admins.platform.impl.jpa;

import de.esports.aeq.admins.platform.api.entity.PlatformTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformRepository extends JpaRepository<PlatformTa, Long> {

    Optional<PlatformTa> findByType(String type);

    Optional<PlatformTa> findByName(String name);
}
