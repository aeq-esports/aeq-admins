package de.esports.aeq.admins.common;

import java.util.Optional;

public final class Args {

    private Args() {

    }

    public static <T> Optional<T> coalesce(T... items) {
        for (T item : items) {
            if (item != null) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }
}
