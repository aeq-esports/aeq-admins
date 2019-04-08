package de.esports.aeq.admins.account.impl.jpa;

import de.esports.aeq.account.api.AccountId;
import de.esports.aeq.account.api.jpa.AccountTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;

@Repository
public interface AccountRepository extends JpaRepository<AccountTa, AccountId> {

    Collection<AccountTa> findAllByAccountId(AccountId accountId);

    Collection<AccountTa> findAllByLastSeenAfter(Instant lastSeen);

    Collection<AccountTa> findAllByAccountIdType(String type);

    Collection<AccountTa> findAllByAccountIdTypeAndLastSeenAfter(
            String type, Instant lastSeen);
}
