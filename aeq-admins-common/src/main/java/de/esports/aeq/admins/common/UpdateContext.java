package de.esports.aeq.admins.common;

public class UpdateContext<T> {

    private final T previous;
    private final T current;

    public static <T> UpdateContext<T> of(T previous, T current) {
        return new UpdateContext<>(previous, current);
    }

    public UpdateContext(T previous, T current) {
        this.previous = previous;
        this.current = current;
    }

    public T getPrevious() {
        return previous;
    }

    public T getCurrent() {
        return current;
    }
}
