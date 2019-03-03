package de.esports.aeq.admins.members.service;

import de.esports.aeq.admins.members.domain.Account;
import de.esports.aeq.admins.members.domain.AccountId;
import de.esports.aeq.admins.members.domain.Complaint;

import java.time.Instant;
import java.util.Collection;

public interface AccountService {

    Collection<Account> getAccounts();

    Collection<Account> getAccounts(Instant lastSeenAt);

    Account getAccountById(AccountId accountId);

    Collection<Account> getAccountsByType(String type);

    Collection<Account> getAccountsByType(String type, Instant lastSeenAt);

    Account createAccount(Account account);

    void deleteAccount(AccountId accountId);

    //-----------------------------------------------------------------------

    Collection<Complaint> getComplaints();

    /**
     * Obtains all complaints that have been associated to this account.
     * <p>
     * More specifically, returns all complaints that list the holder of this account as one of the
     * <i>accused</i>.
     *
     * @return a <code>Collection</code> of complaints, which may be empty, but never
     * <code>null</code>
     */
    Collection<Complaint> getComplaintsByAccused(AccountId accountId);

    Collection<Complaint> getComplaintsByAccuser(AccountId accountId);

    /**
     * Associates a new complaint with this account.
     *
     * @param complaint the complaint to be added, must not be <code>null</code>
     */
    Complaint addComplaint(Complaint complaint);
}
