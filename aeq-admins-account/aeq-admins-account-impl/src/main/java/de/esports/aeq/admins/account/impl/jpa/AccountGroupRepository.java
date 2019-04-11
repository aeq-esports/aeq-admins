package de.esports.aeq.admins.account.impl.jpa;

import de.esports.aeq.admins.account.impl.jpa.entity.AccountGroupTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountGroupRepository extends JpaRepository<AccountGroupTa, Long> {

    @Query(name = "select AccountGroupTa ag from aeq_account_group where")
    List<AccountGroupTa> findAllByAccountsAccountIdPla();
}
