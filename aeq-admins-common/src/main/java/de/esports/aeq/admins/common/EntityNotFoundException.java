package de.esports.aeq.admins.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super("The requested entity cannot be found.");
    }

    public EntityNotFoundException(Object id) {
        super("The requested entity with id " + id + " cannot be found.");
    }
}
