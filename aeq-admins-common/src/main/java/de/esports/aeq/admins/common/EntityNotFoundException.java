package de.esports.aeq.admins.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super("The requested entity cannot be found.");
    }

    public EntityNotFoundException(String message) {
        super("The requested entity cannot be found: " + message);
    }

    public EntityNotFoundException(Object object) {
        super("The requested entity with property '" + object + "' cannot be found.");
    }
}
