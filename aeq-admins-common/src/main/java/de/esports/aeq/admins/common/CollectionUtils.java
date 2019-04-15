package de.esports.aeq.admins.common;

import java.util.Collection;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public final class CollectionUtils {

    /**
     * Attempts to return the first element in a collection.
     *
     * @param collection the collection, not <code>null</code>
     * @param <T>        the type of elements in the collection
     * @return an {@link Optional} that either holds the first element or is <i>empty</i> if  the
     * collection is empty
     */
    public static <T> Optional<T> peek(Collection<T> collection) {
        requireNonNull(collection);
        if (collection.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(collection.iterator().next());
    }

    private CollectionUtils() {
        // prevent instantiation
    }
}
