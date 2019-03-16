package de.esports.aeq.admins.members.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingAccountIdException extends RuntimeException {

    public MissingAccountIdException() {
        super();
    }

    public MissingAccountIdException(String message) {
        super(message);
    }
}
