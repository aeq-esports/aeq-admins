package de.esports.aeq.admins.common;

import org.springframework.data.domain.Example;

import java.util.function.Function;

public final class ExampleTransformer {

    /**
     * Transforms a given example by applying a function to its probe.
     *
     * @param example the example, not <code>null</code>
     * @param mapper  a mapping function that transforms the target type <code>T</code> to the
     *                resulting type <code>R</code>, not <code>null</code>
     * @param <T>     the target type
     * @param <R>     the resulting type
     * @return a transformed example {@link Example} of type <code>R</code>
     */
    public static <T, R> Example<R> transform(Example<T> example, Function<T, R> mapper) {
        R transformedProbe = mapper.apply(example.getProbe());
        return Example.of(transformedProbe, example.getMatcher());
    }

    private ExampleTransformer() {
        // prevent instantiation
    }
}
