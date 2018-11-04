package de.esports.aeq.admins.common;

public class RestPreconditions {

    public static <T> T checkParameterNotNull(T object, String name) {
        if (object == null) {
            throw new MissingParameterException(name);
        }
        return object;
    }
}
