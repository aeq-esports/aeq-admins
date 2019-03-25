package de.esports.aeq.admins.integration.riot.jpa;

import de.esports.aeq.admins.integration.riot.jpa.entity.RiotPlatformInstanceTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiotPlatformInstanceRepository extends
        JpaRepository<RiotPlatformInstanceTa, Long> {

    Optional<RiotPlatformInstanceTa> findByRegionId(String regionId);
}
