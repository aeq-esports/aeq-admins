package de.esports.aeq.admins.security.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException() {
        super();
    }

    public DuplicateEntityException(Object entity) {
        super("Duplicated entity: " + entity.toString());
    }

    public DuplicateEntityException(String entity) {
        super("Duplicated entity: " + entity);
    }
}
