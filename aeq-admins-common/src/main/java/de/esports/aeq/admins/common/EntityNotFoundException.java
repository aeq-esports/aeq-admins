package de.esports.aeq.admins.common;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super("The requested entity cannot be found.");
    }

    public EntityNotFoundException(Object id) {
        super("The requested entity with id " + id + " cannot be found.");
    }
}
