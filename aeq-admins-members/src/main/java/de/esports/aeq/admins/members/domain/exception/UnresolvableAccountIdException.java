package de.esports.aeq.admins.members.domain.exception;

import de.esports.aeq.admins.members.domain.AccountId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Indicates that an account id cannot be resolved.
 * <p>
 * The cause of this may be that the target account value does not exist or does not exist for that
 * specific platform.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnresolvableAccountIdException extends RuntimeException {

    /**
     * Constructs a new exception.
     *
     * @param accountId the account id
     */
    public UnresolvableAccountIdException(AccountId accountId) {
        super(createMessage(accountId));
    }

    private static String createMessage(AccountId accountId) {
        return "Cannot resolve account id: " + accountId.toString();
    }
}
