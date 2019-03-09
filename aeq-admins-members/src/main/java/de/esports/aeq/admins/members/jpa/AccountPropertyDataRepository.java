package de.esports.aeq.admins.members.jpa;

import de.esports.aeq.admins.members.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.members.jpa.entity.AccountPropertyDataTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPropertyDataRepository extends
        JpaRepository<AccountPropertyDataTa, AccountIdTa> {

}
