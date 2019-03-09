package de.esports.aeq.admins.members.jpa;

import de.esports.aeq.admins.members.domain.account.AccountId;
import de.esports.aeq.admins.members.jpa.entity.AccountTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;

@Repository
public interface AccountRepository extends JpaRepository<AccountTa, AccountId> {

    Collection<AccountTa> findAllByLastSeenAfter(Instant lastSeen);

    Collection<AccountTa> findAllByAccountIdType(String type);

    Collection<AccountTa> findAllByAccountIdTypeAndLastSeenAfter(
            String type, Instant lastSeen);
}
