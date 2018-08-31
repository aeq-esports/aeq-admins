package de.esports.aeq.admins.application.jpa;

import de.esports.aeq.admins.application.domain.ApplicationStatus;
import de.esports.aeq.admins.application.domain.ApplicationTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationTa, Long> {

    @Query(name = "")
    List<ApplicationTa> findAllByStatus(ApplicationStatus status);
}
