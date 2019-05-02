package de.esports.aeq.admins.account.impl.jpa;

import de.esports.aeq.admins.account.api.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.account.impl.jpa.entity.ConnectedAccountTa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

public interface ConnectedAccountRepository extends JpaRepository<ConnectedAccountTa, AccountIdTa> {

    Collection<ConnectedAccountTa> findAllByAccountIdIn(Set<AccountIdTa> accountIds);
}
