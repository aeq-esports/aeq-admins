package de.esports.aeq.admins.trials.exception;

public class AlreadyVotedException extends RuntimeException {

    public AlreadyVotedException() {
        super();
    }

    public AlreadyVotedException(String message) {
        super(message);
    }
}
