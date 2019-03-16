package de.esports.aeq.admins.members.domain.exception;

public class UnresolvableAccountException extends Exception {

    public UnresolvableAccountException() {
    }

    public UnresolvableAccountException(String message) {
        super(message);
    }
}
