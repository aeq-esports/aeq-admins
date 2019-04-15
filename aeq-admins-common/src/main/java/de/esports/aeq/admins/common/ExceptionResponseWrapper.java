package de.esports.aeq.admins.common;

public final class ExceptionResponseWrapper {

    public static RuntimeException notFound(Object id) {
        return new EntityNotFoundException(id);
    }

    private ExceptionResponseWrapper() {
        // prevent instantiation
    }
}
