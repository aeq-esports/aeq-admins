package de.esports.aeq.admins.members.domain.exception;

import de.esports.aeq.admins.members.domain.account.AccountId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountIdAlreadyOccupiedException extends RuntimeException {

    private AccountId accountId;

    public AccountIdAlreadyOccupiedException(AccountId accountId) {
        super(createMessage(accountId));
        this.accountId = accountId;
    }

    private static String createMessage(AccountId accountId) {
        return "Account id " + accountId.toString() + " is already occupied.";
    }
}
