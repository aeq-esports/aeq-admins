package de.esports.aeq.admins.security.api.exception;

public class DuplicateUsernameException extends RuntimeException {

    private final String username;

    public DuplicateUsernameException(String username) {
        super("The username " + username + " is already taken.");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
