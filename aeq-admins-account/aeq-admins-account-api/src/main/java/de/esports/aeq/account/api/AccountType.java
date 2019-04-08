package de.esports.aeq.account.api;

public enum AccountType {

    /**
     * Accounts that are related to registered members.
     */
    MEMBER,
    /**
     * Accounts that are only temporary and exist purely to reference objects created by untrusted
     * account data.
     */
    TEMPORARY
}
