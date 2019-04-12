package de.esports.aeq.admins.account.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountIdException extends RuntimeException {

    public AccountIdException() {
        super();
    }

    public AccountIdException(String message) {
        super(message);
    }
}
