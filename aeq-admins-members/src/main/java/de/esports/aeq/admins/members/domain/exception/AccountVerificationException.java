package de.esports.aeq.admins.members.domain.exception;

import de.esports.aeq.admins.members.domain.account.VerifiableAccountImpl;

public class AccountVerificationException extends Exception {

    public AccountVerificationException(VerifiableAccountImpl account) {
        super(createMessage(account));
    }

    private static String createMessage(VerifiableAccountImpl account) {
        return "Account can not be verified " + account.toString();
    }
}
