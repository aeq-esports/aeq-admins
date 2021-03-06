package de.esports.aeq.admins.account.api.exception;


import de.esports.aeq.admins.account.api.VerifiableAccount;

public class AccountVerificationException extends Exception {

    public AccountVerificationException(VerifiableAccount account) {
        super(createMessage(account));
    }

    private static String createMessage(VerifiableAccount account) {
        return "Account can not be verified " + account.toString();
    }
}
