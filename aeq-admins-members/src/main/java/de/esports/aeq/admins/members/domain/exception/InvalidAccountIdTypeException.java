package de.esports.aeq.admins.members.domain.exception;

public class InvalidAccountIdTypeException extends AccountIdException {

    public InvalidAccountIdTypeException(String expected, String actual) {
        super(createMessage(expected, actual));
    }

    private static String createMessage(String expected, String actual) {
        return "Expected account id of type " + expected + " but actual type is " + actual;
    }
}
