package de.esports.aeq.admins.member.web.dto;

import de.esports.aeq.admins.member.api.ConnectedAccount;
import de.esports.aeq.admins.member.api.MemberAccount;
import de.esports.aeq.admins.member.api.MemberDetails;

import java.util.Collection;
import java.util.HashSet;

public class MemberDto {

    private MemberAccount account;
    private MemberDetails details;
    private Collection<ConnectedAccount> connectedAccounts = new HashSet<>();

    public MemberAccount getAccount() {
        return account;
    }

    public void setAccount(MemberAccount account) {
        this.account = account;
    }

    public MemberDetails getDetails() {
        return details;
    }

    public void setDetails(MemberDetails details) {
        this.details = details;
    }

    public Collection<ConnectedAccount> getConnectedAccounts() {
        return connectedAccounts;
    }

    public void setConnectedAccounts(
            Collection<ConnectedAccount> connectedAccounts) {
        this.connectedAccounts = connectedAccounts;
    }
}
