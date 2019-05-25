package de.esports.aeq.admins.common;

public class DuplicateKeyException extends RuntimeException {

    public Object key;

    public DuplicateKeyException(Object key) {
        this.key = key;
    }
}
