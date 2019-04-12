package de.esports.aeq.admins.account.impl.jpa;

import de.esports.aeq.admins.account.api.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.account.api.jpa.entity.AccountTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;

@Repository
public interface AccountRepository extends JpaRepository<AccountTa, Long> {

    Collection<AccountTa> findAllByAccountId(AccountIdTa accountId);

    Collection<AccountTa> findAllByLastSeenAfter(Instant lastSeen);

    @Query("select acc from AccountTa acc inner join AccountIdTa acc_id on acc.id = acc_id.id " +
            "where acc_id.type = :type")
    Collection<AccountTa> findAllByAccountIdType(String type);

    @Query("select acc from AccountTa acc inner join AccountIdTa acc_id on acc.id = acc_id.id " +
            "where acc_id.type = :type and acc.lastSeen >= :lastSeen")
    Collection<AccountTa> findAllByAccountIdTypeAndLastSeenAfter(
            String type, Instant lastSeen);

    void deleteByAccountId(AccountIdTa accountId);
}
