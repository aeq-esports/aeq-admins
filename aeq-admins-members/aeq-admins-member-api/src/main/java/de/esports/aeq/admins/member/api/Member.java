package de.esports.aeq.admins.member.api;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static java.util.Objects.requireNonNull;

/**
 * Represents a member.
 */
public class Member {

    private MemberAccount account;
    private MemberDetails details;

    private Collection<ConnectedAccount> connectedAccounts = new HashSet<>();

    public MemberAccount getAccount() {
        return account;
    }

    public void setAccount(MemberAccount account) {
        this.account = account;
    }

    /**
     * Obtains additional details, mostly personal information about this member.
     *
     * @return the details, not <code>null</code>
     */
    public MemberDetails getDetails() {
        return details;
    }

    public void setDetails(MemberDetails details) {
        this.details = requireNonNull(details);
    }

    /**
     * Obtains an <i>unmodifiable</i> view of all connectedAccounts that belong to this user.
     *
     * @return
     */
    public Collection<ConnectedAccount> getConnectedAccounts() {
        return Collections.unmodifiableCollection(connectedAccounts);
    }

    public void addAccount(ConnectedAccount account) {
        connectedAccounts.add(requireNonNull(account));
    }
}
